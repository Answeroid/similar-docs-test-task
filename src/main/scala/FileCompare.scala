import scala.io.Source
import java.io.File
import scala.collection.mutable._


object FileCompare {

    def filesIterator(d: File): Array[File] = {
        val (dirs, files) =  d.listFiles.partition(_.isDirectory)
        files ++ dirs.flatMap(filesIterator)
    }

    def main(args: Array[String]): Unit = {

        try {

            val dir = new File(System.getProperty("user.dir") + "/testfiles/")
            var stringsList = ListBuffer.empty[String]
            val compareStrings = new Levenshtein

            for(file_ <- filesIterator(dir)) {
                var lines = Source.fromFile(file_).getLines.mkString
                    .toLowerCase.replaceAll("[^A-Za-z0-9 ]", "")
                stringsList += lines
            }

            //println(compareStrings.distance(stringsList.head, stringsList(2)))

            // loop to compare elements with each other
            for (a <- stringsList; b <- stringsList) {
                println(("Levenshtein distance between Strings:\n" +
                    "1) %s\n" +
                    "2) %s\n" +
                    "Equals: %s\n").format(a, b, compareStrings.distance(a, b)))
            }

        } catch {
            case ex: NullPointerException => ex.printStackTrace()
        }
    }
}
