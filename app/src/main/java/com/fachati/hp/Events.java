package com.fachati.hp;


public class Events {
    private Events(){

    }

    public static class ChangedListNotify {
        public ChangedListNotify() {
        }
    }

    public static class ScrollDirection {
        public final int direction;

        public ScrollDirection(int direction) {
            this.direction = direction;
        }
    }

    public static class UpdateListBookOnResume {
        public UpdateListBookOnResume() {
        }
    }

}
