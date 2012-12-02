package ca.ilanguage.oprime.content;

public class PageUrlGetPair {
  String filename;
  String getString;
  String delimiter = "#";//"\\?"

  public String getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getGetString() {
    return getString;
  }

  public void setGetString(String getString) {
    this.getString = getString;
  }

  public PageUrlGetPair(String filename, String getString) {
    super();
    this.filename = filename;
    this.getString = getString;
  }

  public String toString() {
    return this.filename + this.delimiter + this.getString;
  }

}
