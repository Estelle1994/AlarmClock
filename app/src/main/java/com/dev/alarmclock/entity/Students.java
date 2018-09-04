package com.dev.alarmclock.entity;

import java.util.List;

/**
 * Created by ${Estelle} on 2018/6/22.
 */

public class Students {

    /**
     * name : John
     * grade : [{"course":"English","score":100},{"course":"Math","score":78}]
     */

    private String name;
    private List<GradeBean> grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GradeBean> getGrade() {
        return grade;
    }

    public void setGrade(List<GradeBean> grade) {
        this.grade = grade;
    }

    public static class GradeBean {
        /**
         * course : English
         * score : 100
         */

        private String course;
        private int score;

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
