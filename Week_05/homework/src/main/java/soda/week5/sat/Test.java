package soda.week5.sat;

public class Test {
    private String name;

    public static class Builder {
        private Test test = new Test();

        public Builder setName(String name) {
            test.setName(name);
            return this;
        }

        public Test builder() {
            return test;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Builder builder = new Builder();
        Builder builder2 = new Builder();

        System.out.println(builder);
        System.out.println(builder2);
        System.out.println(builder.builder());
        System.out.println(builder2.builder());
    }
}
