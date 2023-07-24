package com.ms.tourist_app.application.constants;

public class DevMessageConstant {
  private DevMessageConstant() {
  }

  public static final class Category {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found category by id = %s";
    public static final String NAME_IS_EXIST = "Category with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate category name '%s'";
  }

  public static final class Subject {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found subject by id = %s";
    public static final String DUPLICATE_NAME = "Duplicate subject name '%s'";
  }

  public static final class Lesson {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found lesson by id = %s";
    public static final String ERR_NOT_FOUND_LESSON_BY_SUBJECT_ID = "Not found lesson by subject id = %s";
  }

  public static final class LessonStudent {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found lesson student by id = %s";
  }

  public static final class Comment {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found comment by id = %s";
    public static final String ERR_NOT_YOURS = "Comment id = '%s' is not yours.";
  }

  public static final class Notification {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found notification by id = %s";
  }

  public static final class Schedule {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found schedule by id = %s";
  }

  public static final class Role {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found role by id = %s";
    public static final String NAME_IS_EXIST = "Role with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate role name '%s'";
  }

  public static final class User {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found user student by id = %s";
  }

  public static final class UserSubjectRelation {
    public static final String CAN_NOT_REMOVE_OBJECT = "Can not remove object with userId = %s and subjectId = %s";
  }

  public static final class SettingByKey{
    public static final String ERR_NOT_FOUND_BY_KEY = "Not found setting by key = %s";
  }

}
