package edu.brown.cs.student.main.recommender;

import java.util.List;

public interface Item {
  List<String> getVectorRepresentation();
  Integer getId();
}
