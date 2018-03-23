package com.elead.module;



public class MYModel  {

    /**
     *
     */
    private static final long serialVersionUID = -4058647909326839653L;

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MYModel() {
        super();
        // TODO Auto-generated constructor stub
    }

//    public MYModel(String tableName) {
//        super(tableName);
//        // TODO Auto-generated constructor stub
//    }

    public MYModel(int age) {
        super();
        this.age = age;
    }

}
