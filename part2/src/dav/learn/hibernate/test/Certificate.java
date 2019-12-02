package dav.learn.hibernate.test;

public class Certificate {

  private int id;
  private String name;

  public Certificate() {}

  public Certificate(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!this.getClass().equals(obj.getClass())) return false;

    Certificate objCert = (Certificate) obj;

    return (this.id == objCert.getId())
        && (this.name == null ? objCert.getName() == null : this.name.equals(objCert.getName()));
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + (name == null ? 0 : name.hashCode());

    return result;
  }

}
