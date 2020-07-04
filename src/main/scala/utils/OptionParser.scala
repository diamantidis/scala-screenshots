package utils

import scala.util.{Failure, Success, Try}

sealed trait OptionFailure { def description: String }

object OptionFailure {

  val usage =
    """
 $$$$$$\                                                              $$\                   $$\     
$$  __$$\                                                             $$ |                  $$ |    
$$ /  \__| $$$$$$$\  $$$$$$\   $$$$$$\   $$$$$$\  $$$$$$$\   $$$$$$$\ $$$$$$$\   $$$$$$\  $$$$$$\   
\$$$$$$\  $$  _____|$$  __$$\ $$  __$$\ $$  __$$\ $$  __$$\ $$  _____|$$  __$$\ $$  __$$\ \_$$  _|  
 \____$$\ $$ /      $$ |  \__|$$$$$$$$ |$$$$$$$$ |$$ |  $$ |\$$$$$$\  $$ |  $$ |$$ /  $$ |  $$ |    
$$\   $$ |$$ |      $$ |      $$   ____|$$   ____|$$ |  $$ | \____$$\ $$ |  $$ |$$ |  $$ |  $$ |$$\ 
\$$$$$$  |\$$$$$$$\ $$ |      \$$$$$$$\ \$$$$$$$\ $$ |  $$ |$$$$$$$  |$$ |  $$ |\$$$$$$  |  \$$$$  |
 \______/  \_______|\__|       \_______| \_______|\__|  \__|\_______/ \__|  \__| \______/    \____/ 

    Usage: screenshots [--config filename]
"""

  case object NoArgument extends OptionFailure { var description = usage }
  case object InvalidArgument extends OptionFailure { var description = usage }
}

trait OptionParser {
  def getOptionParser: OptionParser.Service
}

object OptionParser {

  trait Service {
    def parse(args: Seq[String]): Either[OptionFailure, Map[Symbol, String]]
  }

  private case class LiveService() extends OptionParser.Service {

    override final def parse(
      args: Seq[String]
    ): Either[OptionFailure, Map[Symbol, String]] = {
      if (args.length == 0)
        return Left(OptionFailure.NoArgument)

      val arglist = args.toList
      type OptionMap = Map[Symbol, String]

      def nextOption(map: OptionMap, list: List[String]): Try[OptionMap] =
        Try {
          list match {
            case Nil => map
            case "--config" :: value :: tail =>
              nextOption(map ++ Map('config -> value), tail) match {
                case Success(value)     => value
                case Failure(exception) => throw new Exception(exception)
              }
            case option :: tail =>
              throw new Exception("Unknown option" + option)

          }
        }

      val result: Either[OptionFailure, Map[Symbol, String]] =
        nextOption(Map(), arglist) match {
          case Success(value)     => Right(value)
          case Failure(exception) => Left(OptionFailure.InvalidArgument)
        }
      return result
    }
  }

  trait Live extends OptionParser {
    override def getOptionParser: Service = OptionParser.LiveService()
  }
}
