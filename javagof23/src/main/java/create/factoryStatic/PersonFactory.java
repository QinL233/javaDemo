package create.factoryStatic;

public class PersonFactory implements Factory {
    @Override
    public void createMan() {
        new Man().getName();
    }

    @Override
    public void createWoman() {
        new Woman().getName();
    }

    //通过类名获取对象
    @Override
    public void create(String name) {
        if(name == null || "".equals(name.trim())){
            System.out.println("create null");
        }else{
            String str = this.getClass()
                    .getPackage()
                    .toString()
                    .replaceAll("package", "")
                    .trim()
                    +"."+name;
            //System.out.println(str);
            try {
                Class<Person> clz = (Class<Person>) Class.forName(str);
                Person person = clz.newInstance();
                person.getName();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
