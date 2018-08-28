import scala.io.Source
import java.io.File

import scala.collection.mutable.Map
import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap
import util.control.Breaks._


object FileCompare {

    def filesIterator(d: File): Array[File] = {
        val (dirs, files) =  d.listFiles.partition(_.isDirectory)
        files ++ dirs.flatMap(filesIterator)
    }

    def getFilesDir(unit: Unit): File = {
        // returns just hardcoded dir with testfiles

        new File(System.getProperty("user.dir") + "/testfiles/")
    }

    def printClosestFiles(resultsMap: Map[Int, List[String]],
                       filesMap: Map[String, String]): Unit = {
        // printer just prints files that are closest to each other
        // according Levenshtein alg
        val closestMap = ListMap(resultsMap.toSeq.sortWith(_._1 < _._1):_*).take(3)
        closestMap.keys.foreach{key =>
            // iterate over list of compared strings
            println("\nThose files are close to each other:")
            closestMap(key).foreach{text_ =>
                // iterate over filesMap to find closest files
                filesMap.keys.foreach{fileName_ =>
                    if (text_ == filesMap(fileName_)) {
                        println(fileName_)
                    }
                }
            }
            println("Distance: " + key)
        }
    }

    def printClosestPairs(resultsMap: Map[Int, List[String]]): Unit = {
        // sort distances from low to high by key (distance)
        val result = ListMap(resultsMap.toSeq.sortWith(_._1 < _._1):_*)

        // collect only first 3 closest files
        val closestMap = result.take(3)
        println("-----Closest 3 pairs-----")
        closestMap.keys.foreach{key =>
            println("Key = " + key)
            println("Value = " + result(key))
        }
    }

    def calcLevenshteinDistance(stringsList: ListBuffer[String]): Map[Int, List[String]] = {
        // func to compare elements with each other
        // and calculate Levenshtein distance

        // creating immutable object of Levenshtein alg
        val compareStrings = new Levenshtein
        // collecting comparison results (distances for pairs)
        val resultsMap = Map[Int, List[String]]()

        for (a <- stringsList; b <- stringsList) {
            breakable {
                if (a == b) break
                val distance = compareStrings.distance(a, b).toInt
                resultsMap(distance) = List(a, b)
            }
        }
        resultsMap
    }

    def doItCached[A, B](f: A => B): A => B = new HashMap[A, B] {
        // on func with 1 argument returns func with the same type
        // which can cache itself
        override def apply(key: A): B = getOrElseUpdate(key, f(key))
    }

    def getFilesAndContent(dir: File): Map[String, String] = {
        // iterating over files and gathering context with filenames

        val filesMap = Map[String, String]()

        for(file_ <- filesIterator(dir)) {
            // iterating over files and gathering context with filenames
            var lines = Source.fromFile(file_).getLines.mkString
                .toLowerCase.replaceAll("[^A-Za-z0-9 ]", "")
            filesMap(file_.toString) = lines
        }
        filesMap
    }

    def getJustContent(dir: File): ListBuffer[String] = {
        // iterating over files and gathering context

        var stringsList = ListBuffer.empty[String]

        for(file_ <- filesIterator(dir)) {
            // iterating over files and gathering context with filenames
            var lines = Source.fromFile(file_).getLines.mkString
                .toLowerCase.replaceAll("[^A-Za-z0-9 ]", "")
            stringsList += lines
        }
        stringsList
    }

    def main(args: Array[String]): Unit = {

        try {
            val cachedCalcLevenshteinDistance = doItCached(calcLevenshteinDistance)

            printClosestPairs(cachedCalcLevenshteinDistance(getJustContent(getFilesDir())))
            printClosestFiles(cachedCalcLevenshteinDistance(getJustContent(getFilesDir())),
                getFilesAndContent(getFilesDir()))
        } catch {
            case ex: NullPointerException => ex.printStackTrace()
        }
    }
}
