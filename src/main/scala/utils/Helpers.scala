package utils

object Helpers {

  def getFilename(url: String, host: String) =
    url
      .replace(host, "")
      .toLowerCase()
      .replaceAll("^/+", "") // trim leading '/'
      .replaceAll("/+$", "") // trim trailing '/'
      .replaceAll("/", "-") // replace '/' with '-'
      .replaceAll(
        "^$",
        "homepage"
      ) // if it's empty string, then it's the home page
}
