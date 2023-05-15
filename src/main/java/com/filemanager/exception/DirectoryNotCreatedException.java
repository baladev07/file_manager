package com.filemanager.exception;

import net.bytebuddy.implementation.bind.annotation.Super;

public class DirectoryNotCreatedException extends RuntimeException{

  public DirectoryNotCreatedException(String message)
  {
      super(message);
  }
}
