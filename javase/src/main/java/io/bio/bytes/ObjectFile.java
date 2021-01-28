package io.bio.bytes;

import io.Config;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:56
 */
public class ObjectFile {
    public static void main(String[] args) {
        try(
                ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(Config.resources,Config.fileName)));
                ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File(Config.resources,Config.fileName)));
        ) {
            Model model = new Model("admin", "123456");
            oos.writeObject(model);
            oos.flush();
            Model result = (Model) ois.readObject();
            System.out.println(result.getUsername() + result.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


class Model implements Serializable {
    private String username;
    private String password;

    public Model(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
