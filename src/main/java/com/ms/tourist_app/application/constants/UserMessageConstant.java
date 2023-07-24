package com.ms.tourist_app.application.constants;

public class UserMessageConstant {

  public static final String ERR_EXCEPTION_GENERAL = "exception.general";
  public static final String ERR_EXCEPTION_ACCESS_SYSTEM = "exception.access.system";
  public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
  public static final String INVALID_SOME_THING_FIELD = "invalid.general";


  public static final class Category {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.category_id";
  }

  public static final class Comment {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.comment_id";
    public static final String ERR_NOT_YOURS = "invalid.not.your.comment_id";
  }

  public static final class Subject {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.subject_id";
    public static final String INVALID_FIELD_IS_REQUIRED = "invalid.field.is.require";

    public static final String INVALID_FIELD_ENTERED = "invalid.field.not.valid";
  }

  public static final class Schedule {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.schedule_id";
  }

  public static final class Notification {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.notification_id";
  }

  public static final class Lesson {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.lesson_id";

    public static final String ERR_NOT_FOUND_LESSON_BY_SUBJECT_ID = "invalid.not.found.lesson.by.subject.id";
  }

  public static final class LessonStudent {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.lesson_student_id";

    public static final String ERR_NOT_FOUND_LESSON_BY_SUBJECT_ID = "invalid.not.found.lesson.by.subject.id";
  }

  public static final class Role {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.role_id";

    public static final String ERR_NOT_FOUND_LESSON_BY_ROLE_ID = "invalid.not.found.role.by.role.id";
  }

  public static final class User {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.user_id";
  }

  public static final class SettingByKey{
    public static final String ERR_NOT_FOUND_SETTING_BY_KEY = "invalid.not.found.setting_key";
  }
}
