package com.example.danpingji.beans;

/**
 * Created by chenjun on 2017/5/17.
 */

public class ShiBieBean {


    /**
     * face1 : {"rect":{"left":21,"top":46,"width":57,"height":57},"confidence":0.9999989}
     * face2 : {"rect":{"left":-26,"top":205,"width":283,"height":283},"confidence":0.9999981}
     * score : 86.08437
     */

    private Face1Bean face1;
    private Face2Bean face2;
    private double score;

    public Face1Bean getFace1() {
        return face1;
    }

    public void setFace1(Face1Bean face1) {
        this.face1 = face1;
    }

    public Face2Bean getFace2() {
        return face2;
    }

    public void setFace2(Face2Bean face2) {
        this.face2 = face2;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public static class Face1Bean {
        /**
         * rect : {"left":21,"top":46,"width":57,"height":57}
         * confidence : 0.9999989
         */

        private RectBean rect;
        private double confidence;

        public RectBean getRect() {
            return rect;
        }

        public void setRect(RectBean rect) {
            this.rect = rect;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public static class RectBean {
            /**
             * left : 21
             * top : 46
             * width : 57
             * height : 57
             */

            private int left;
            private int top;
            private int width;
            private int height;

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
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
        }
    }

    public static class Face2Bean {
        /**
         * rect : {"left":-26,"top":205,"width":283,"height":283}
         * confidence : 0.9999981
         */

        private RectBeanX rect;
        private double confidence;

        public RectBeanX getRect() {
            return rect;
        }

        public void setRect(RectBeanX rect) {
            this.rect = rect;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public static class RectBeanX {
            /**
             * left : -26
             * top : 205
             * width : 283
             * height : 283
             */

            private int left;
            private int top;
            private int width;
            private int height;

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
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
        }
    }
}
