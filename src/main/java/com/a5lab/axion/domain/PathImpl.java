package com.a5lab.axion.domain;

import jakarta.validation.Path;
import java.util.Iterator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PathImpl implements Path {

  private final String field;

  public String toString() {
    return field;
  }

  @Override
  public Iterator<Node> iterator() {
    return null;
  }
}
