import scala.io.Source
import java.io.File
import scala.collection.mutable._


object FileCompare {

    def filesIterator(d: File): Array[File] = {
        val (dirs, files) =  d.listFiles.partition(_.isDirectory)
        files ++ dirs.flatMap(filesIterator)
    }

    def main(args: Array[String]): Unit = {

        val dir = new File(System.getProperty("user.dir") + "/testfiles/")
        println("____________________________")
        println(System.getProperty("user.dir"))
        var stringsList = ListBuffer.empty[String]
        val compareStrings = new Levenshtein


        for(file_ <- filesIterator(dir)) {
            var lines = Source.fromFile(file_).getLines.mkString
                .toLowerCase.replaceAll("[^A-Za-z0-9 ]", "")
            stringsList += lines
        }

        // comparison works but need to compare all files
        // automatically between each other
        println(compareStrings.distance(stringsList.head, stringsList(2)))
    }
}
