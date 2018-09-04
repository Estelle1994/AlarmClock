package com.dev.alarmclock.entity;

import java.util.List;

/**
 * Created by ${Estelle} on 2018/6/21.
 */

public class FaceInfoEntity {

    /**
     * ret : 0
     * msg : ok
     * data : {"image_width":"530","image_height":"397","face":[{"face_id":"2634899042855837839","x":132,"y":84,"width":135,"height":135,"gender":99,"age":38,"expression":24,"beauty":84,"glass":0,"pitch":12,"yaw":14,"roll":4294967292,"face_shape":{"face_profile":[{"x":149,"y":132},{"x":149,"y":143},{"x":150,"y":155},{"x":152,"y":167},{"x":155,"y":178},{"x":161,"y":188},{"x":168,"y":197},{"x":177,"y":205},{"x":187,"y":212},{"x":198,"y":216},{"x":209,"y":217},{"x":218,"y":213},{"x":226,"y":207},{"x":232,"y":199},{"x":238,"y":191},{"x":242,"y":183},{"x":246,"y":174},{"x":249,"y":164},{"x":251,"y":154},{"x":252,"y":145},{"x":252,"y":136}],"left_eye":[{"x":179,"y":132},{"x":183,"y":135},{"x":188,"y":137},{"x":193,"y":137},{"x":198,"y":135},{"x":194,"y":131},{"x":189,"y":129},{"x":184,"y":129}],"right_eye":[{"x":243,"y":136},{"x":239,"y":139},{"x":235,"y":140},{"x":230,"y":139},{"x":225,"y":137},{"x":229,"y":133},{"x":234,"y":132},{"x":239,"y":133}],"left_eyebrow":[{"x":168,"y":122},{"x":177,"y":122},{"x":186,"y":123},{"x":195,"y":125},{"x":205,"y":126},{"x":197,"y":119},{"x":187,"y":116},{"x":177,"y":116}],"right_eyebrow":[{"x":251,"y":129},{"x":245,"y":127},{"x":238,"y":126},{"x":232,"y":126},{"x":226,"y":125},{"x":232,"y":120},{"x":239,"y":119},{"x":247,"y":122}],"mouth":[{"x":188,"y":184},{"x":195,"y":189},{"x":203,"y":193},{"x":211,"y":194},{"x":218,"y":193},{"x":224,"y":189},{"x":228,"y":184},{"x":223,"y":182},{"x":217,"y":181},{"x":212,"y":182},{"x":207,"y":181},{"x":197,"y":182},{"x":195,"y":186},{"x":203,"y":187},{"x":212,"y":188},{"x":217,"y":187},{"x":223,"y":186},{"x":222,"y":184},{"x":217,"y":184},{"x":212,"y":185},{"x":204,"y":184},{"x":196,"y":184}],"nose":[{"x":217,"y":165},{"x":214,"y":136},{"x":210,"y":143},{"x":206,"y":150},{"x":202,"y":157},{"x":196,"y":165},{"x":206,"y":170},{"x":215,"y":172},{"x":222,"y":170},{"x":228,"y":165},{"x":224,"y":157},{"x":221,"y":150},{"x":217,"y":143}]}}]}
     */

    private int ret;
    private String msg;
    private DataBean data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image_width : 530
         * image_height : 397
         * face : [{"face_id":"2634899042855837839","x":132,"y":84,"width":135,"height":135,"gender":99,"age":38,"expression":24,"beauty":84,"glass":0,"pitch":12,"yaw":14,"roll":4294967292,"face_shape":{"face_profile":[{"x":149,"y":132},{"x":149,"y":143},{"x":150,"y":155},{"x":152,"y":167},{"x":155,"y":178},{"x":161,"y":188},{"x":168,"y":197},{"x":177,"y":205},{"x":187,"y":212},{"x":198,"y":216},{"x":209,"y":217},{"x":218,"y":213},{"x":226,"y":207},{"x":232,"y":199},{"x":238,"y":191},{"x":242,"y":183},{"x":246,"y":174},{"x":249,"y":164},{"x":251,"y":154},{"x":252,"y":145},{"x":252,"y":136}],"left_eye":[{"x":179,"y":132},{"x":183,"y":135},{"x":188,"y":137},{"x":193,"y":137},{"x":198,"y":135},{"x":194,"y":131},{"x":189,"y":129},{"x":184,"y":129}],"right_eye":[{"x":243,"y":136},{"x":239,"y":139},{"x":235,"y":140},{"x":230,"y":139},{"x":225,"y":137},{"x":229,"y":133},{"x":234,"y":132},{"x":239,"y":133}],"left_eyebrow":[{"x":168,"y":122},{"x":177,"y":122},{"x":186,"y":123},{"x":195,"y":125},{"x":205,"y":126},{"x":197,"y":119},{"x":187,"y":116},{"x":177,"y":116}],"right_eyebrow":[{"x":251,"y":129},{"x":245,"y":127},{"x":238,"y":126},{"x":232,"y":126},{"x":226,"y":125},{"x":232,"y":120},{"x":239,"y":119},{"x":247,"y":122}],"mouth":[{"x":188,"y":184},{"x":195,"y":189},{"x":203,"y":193},{"x":211,"y":194},{"x":218,"y":193},{"x":224,"y":189},{"x":228,"y":184},{"x":223,"y":182},{"x":217,"y":181},{"x":212,"y":182},{"x":207,"y":181},{"x":197,"y":182},{"x":195,"y":186},{"x":203,"y":187},{"x":212,"y":188},{"x":217,"y":187},{"x":223,"y":186},{"x":222,"y":184},{"x":217,"y":184},{"x":212,"y":185},{"x":204,"y":184},{"x":196,"y":184}],"nose":[{"x":217,"y":165},{"x":214,"y":136},{"x":210,"y":143},{"x":206,"y":150},{"x":202,"y":157},{"x":196,"y":165},{"x":206,"y":170},{"x":215,"y":172},{"x":222,"y":170},{"x":228,"y":165},{"x":224,"y":157},{"x":221,"y":150},{"x":217,"y":143}]}}]
         */

        private String image_width;
        private String image_height;
        private List<FaceBean> face;

        public String getImage_width() {
            return image_width;
        }

        public void setImage_width(String image_width) {
            this.image_width = image_width;
        }

        public String getImage_height() {
            return image_height;
        }

        public void setImage_height(String image_height) {
            this.image_height = image_height;
        }

        public List<FaceBean> getFace() {
            return face;
        }

        public void setFace(List<FaceBean> face) {
            this.face = face;
        }

        public static class FaceBean {
            /**
             * face_id : 2634899042855837839
             * x : 132
             * y : 84
             * width : 135
             * height : 135
             * gender : 99
             * age : 38
             * expression : 24
             * beauty : 84
             * glass : 0
             * pitch : 12
             * yaw : 14
             * roll : 4294967292
             * face_shape : {"face_profile":[{"x":149,"y":132},{"x":149,"y":143},{"x":150,"y":155},{"x":152,"y":167},{"x":155,"y":178},{"x":161,"y":188},{"x":168,"y":197},{"x":177,"y":205},{"x":187,"y":212},{"x":198,"y":216},{"x":209,"y":217},{"x":218,"y":213},{"x":226,"y":207},{"x":232,"y":199},{"x":238,"y":191},{"x":242,"y":183},{"x":246,"y":174},{"x":249,"y":164},{"x":251,"y":154},{"x":252,"y":145},{"x":252,"y":136}],"left_eye":[{"x":179,"y":132},{"x":183,"y":135},{"x":188,"y":137},{"x":193,"y":137},{"x":198,"y":135},{"x":194,"y":131},{"x":189,"y":129},{"x":184,"y":129}],"right_eye":[{"x":243,"y":136},{"x":239,"y":139},{"x":235,"y":140},{"x":230,"y":139},{"x":225,"y":137},{"x":229,"y":133},{"x":234,"y":132},{"x":239,"y":133}],"left_eyebrow":[{"x":168,"y":122},{"x":177,"y":122},{"x":186,"y":123},{"x":195,"y":125},{"x":205,"y":126},{"x":197,"y":119},{"x":187,"y":116},{"x":177,"y":116}],"right_eyebrow":[{"x":251,"y":129},{"x":245,"y":127},{"x":238,"y":126},{"x":232,"y":126},{"x":226,"y":125},{"x":232,"y":120},{"x":239,"y":119},{"x":247,"y":122}],"mouth":[{"x":188,"y":184},{"x":195,"y":189},{"x":203,"y":193},{"x":211,"y":194},{"x":218,"y":193},{"x":224,"y":189},{"x":228,"y":184},{"x":223,"y":182},{"x":217,"y":181},{"x":212,"y":182},{"x":207,"y":181},{"x":197,"y":182},{"x":195,"y":186},{"x":203,"y":187},{"x":212,"y":188},{"x":217,"y":187},{"x":223,"y":186},{"x":222,"y":184},{"x":217,"y":184},{"x":212,"y":185},{"x":204,"y":184},{"x":196,"y":184}],"nose":[{"x":217,"y":165},{"x":214,"y":136},{"x":210,"y":143},{"x":206,"y":150},{"x":202,"y":157},{"x":196,"y":165},{"x":206,"y":170},{"x":215,"y":172},{"x":222,"y":170},{"x":228,"y":165},{"x":224,"y":157},{"x":221,"y":150},{"x":217,"y":143}]}
             */

            private String face_id;
            private int x;
            private int y;
            private int width;
            private int height;
            private int gender;
            private int age;
            private int expression;
            private int beauty;
            private int glass;
            private int pitch;
            private int yaw;
            private long roll;
            private FaceShapeBean face_shape;

            public String getFace_id() {
                return face_id;
            }

            public void setFace_id(String face_id) {
                this.face_id = face_id;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getExpression() {
                return expression;
            }

            public void setExpression(int expression) {
                this.expression = expression;
            }

            public int getBeauty() {
                return beauty;
            }

            public void setBeauty(int beauty) {
                this.beauty = beauty;
            }

            public int getGlass() {
                return glass;
            }

            public void setGlass(int glass) {
                this.glass = glass;
            }

            public int getPitch() {
                return pitch;
            }

            public void setPitch(int pitch) {
                this.pitch = pitch;
            }

            public int getYaw() {
                return yaw;
            }

            public void setYaw(int yaw) {
                this.yaw = yaw;
            }

            public long getRoll() {
                return roll;
            }

            public void setRoll(long roll) {
                this.roll = roll;
            }

            public FaceShapeBean getFace_shape() {
                return face_shape;
            }

            public void setFace_shape(FaceShapeBean face_shape) {
                this.face_shape = face_shape;
            }

            public static class FaceShapeBean {
                private List<FaceProfileBean> face_profile;
                private List<LeftEyeBean> left_eye;
                private List<RightEyeBean> right_eye;
                private List<LeftEyebrowBean> left_eyebrow;
                private List<RightEyebrowBean> right_eyebrow;
                private List<MouthBean> mouth;
                private List<NoseBean> nose;

                public List<FaceProfileBean> getFace_profile() {
                    return face_profile;
                }

                public void setFace_profile(List<FaceProfileBean> face_profile) {
                    this.face_profile = face_profile;
                }

                public List<LeftEyeBean> getLeft_eye() {
                    return left_eye;
                }

                public void setLeft_eye(List<LeftEyeBean> left_eye) {
                    this.left_eye = left_eye;
                }

                public List<RightEyeBean> getRight_eye() {
                    return right_eye;
                }

                public void setRight_eye(List<RightEyeBean> right_eye) {
                    this.right_eye = right_eye;
                }

                public List<LeftEyebrowBean> getLeft_eyebrow() {
                    return left_eyebrow;
                }

                public void setLeft_eyebrow(List<LeftEyebrowBean> left_eyebrow) {
                    this.left_eyebrow = left_eyebrow;
                }

                public List<RightEyebrowBean> getRight_eyebrow() {
                    return right_eyebrow;
                }

                public void setRight_eyebrow(List<RightEyebrowBean> right_eyebrow) {
                    this.right_eyebrow = right_eyebrow;
                }

                public List<MouthBean> getMouth() {
                    return mouth;
                }

                public void setMouth(List<MouthBean> mouth) {
                    this.mouth = mouth;
                }

                public List<NoseBean> getNose() {
                    return nose;
                }

                public void setNose(List<NoseBean> nose) {
                    this.nose = nose;
                }

                public static class FaceProfileBean {
                    /**
                     * x : 149
                     * y : 132
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }

                public static class LeftEyeBean {
                    /**
                     * x : 179
                     * y : 132
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }

                public static class RightEyeBean {
                    /**
                     * x : 243
                     * y : 136
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }

                public static class LeftEyebrowBean {
                    /**
                     * x : 168
                     * y : 122
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }

                public static class RightEyebrowBean {
                    /**
                     * x : 251
                     * y : 129
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }

                public static class MouthBean {
                    /**
                     * x : 188
                     * y : 184
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }

                public static class NoseBean {
                    /**
                     * x : 217
                     * y : 165
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }
            }
        }
    }
}
