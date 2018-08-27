import scala.io.Source
import java.io.File

import scala.collection.mutable.Map
import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer
import util.control.Breaks._


object FileCompare {

    def filesIterator(d: File): Array[File] = {
        val (dirs, files) =  d.listFiles.partition(_.isDirectory)
        files ++ dirs.flatMap(filesIterator)
    }

    def main(args: Array[String]): Unit = {

        try {
            // getting current dir and to get access to /testfiles/ dir
            val dir = new File(System.getProperty("user.dir") + "/testfiles/")

            // creating immutable object of Levenshtein alg
            val compareStrings = new Levenshtein

            // collecting all files with content to filesMap to process them later
            val filesMap = Map[String, String]()
            // collecting only content in separate List to process it more easily
            var stringsList = ListBuffer.empty[String]
            // collecting comparison results (distances for pairs)
            val resultsMap = Map[Int, List[String]]()

            for(file_ <- filesIterator(dir)) {
                // iterating over files and gathering context with filenames
                var lines = Source.fromFile(file_).getLines.mkString
                    .toLowerCase.replaceAll("[^A-Za-z0-9 ]", "")
                filesMap(file_.toString) = lines
                stringsList += lines
            }

            // loop to compare elements with each other
            // and calculate Levenshtein distance
            for (a <- stringsList; b <- stringsList) {
                breakable {
                    if (a == b) break
                    val distance = compareStrings.distance(a, b).toInt
                    resultsMap(distance) = List(a, b)
                }
            }

            // sort distances from low to high by key (distance)
            val result = ListMap(resultsMap.toSeq.sortWith(_._1 < _._1):_*)
            // print all result values just for debugging purposes
            //result.keys.foreach{k =>
                //println("Key = " + k)
                //println("Value = " + resultsMap(k))}

            // collect only first 3 closest files
            val closestMap = result.take(3)
            println("-----Closest 3 pairs-----")
            closestMap.keys.foreach{key =>
                println("Key = " + key)
                println("Value = " + result(key))
            }

            // find filenames for those results above
            closestMap.keys.foreach{key =>
                // iterate over list of compared strings
                println("Those files are close to each other:")
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

        } catch {
            case ex: NullPointerException => ex.printStackTrace()
        }
    }
}
