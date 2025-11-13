package oop.ex4A3;


import java.util.HashMap;
import java.util.Map;

public class IDTable {
  private final Map<Person, String> personToId = new HashMap<>();
  private final Map<String, Person> idToPerson = new HashMap<>();

  public String getID(Person p) {
    return personToId.get(p);
  }

  public Person getName(String id) {
    return idToPerson.get(id);
  }

  public void register(Person p, String id) {
    // 既存の登録があれば消去
    String oldId = personToId.get(p);
    if (oldId != null) {
      idToPerson.remove(oldId);
      personToId.remove(p);
    }
    Person oldPerson = idToPerson.get(id);
    if (oldPerson != null) {
      personToId.remove(oldPerson);
      idToPerson.remove(id);
    }
    // 新規登録
    personToId.put(p, id);
    idToPerson.put(id, p);
  }

  public boolean remove(Person p) {
    String id = personToId.remove(p);
    if (id != null) {
      idToPerson.remove(id);
      return true;
    }
    return false;
  }

  public boolean remove(String id) {
    Person p = idToPerson.remove(id);
    if (p != null) {
      personToId.remove(p);
      return true;
    }
    return false;
  }
}
