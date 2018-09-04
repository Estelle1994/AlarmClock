package com.dev.alarmclock.entity;

/**
 * Created by ${Estelle} on 2018/7/31.
 */

public class LessonEntity {

    /**
     * author : aa,
     * courseContent : aa
     * courseExplain : aa
     * courseId : 4
     * courseName : aa
     * gradeId : 1
     * isShare : 1
     * publishId : 1
     * subjectId : 1
     * uploadDate : 1532663781000
     * userId : 1
     */

    private String author;
    private String courseContent;
    private String courseExplain;
    private int courseId;
    private String courseName;
    private int gradeId;
    private int isShare;
    private int publishId;
    private int subjectId;
    private long uploadDate;
    private int userId;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseExplain() {
        return courseExplain;
    }

    public void setCourseExplain(String courseExplain) {
        this.courseExplain = courseExplain;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getIsShare() {
        return isShare;
    }

    public void setIsShare(int isShare) {
        this.isShare = isShare;
    }

    public int getPublishId() {
        return publishId;
    }

    public void setPublishId(int publishId) {
        this.publishId = publishId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(long uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
