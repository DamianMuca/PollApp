package pl.com.muca.server.entity;

import java.util.Objects;

public class User {

  private int idHash;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String token;

  public long getIdHash() {
    return idHash;
  }

  public void setIdHash(int idHash) {
    this.idHash = idHash;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return String.format(
        "{"
            + "id=%d, "
            + "firstName='%s', "
            + "lastName='%s', "
            + "password='%s', "
            + "email='%s', "
            + "token='%s'"
            + "}",
        idHash, firstName, lastName, password, email, token);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return email.equals(user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }
}
