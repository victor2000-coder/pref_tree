package com.company;

import com.sun.source.tree.Tree;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        TreeNode root = new TreeNode(' ');
        ArrayList<String> arr = new ArrayList<>();
        root.getAllNumbers("", arr);
        File input = new File("src/com/company/data");
        FileReader reader = new FileReader(input);
        BufferedReader bReader = new BufferedReader(reader);
        String temp = bReader.readLine();
        while (temp != null){
            root.insert(temp);
            temp = bReader.readLine();
        }
        root.recursionDraw(0);
        //writeToFile("C:\\Users\\User\\Documents\\TreeData.txt", root);
    }
    public static class TreeNode{
        char value;
        MyArrayList children;

        public TreeNode(char value) {
            this.children = new MyArrayList();
            this.value = value;
        }
        public void insert(String data){
            if (!"".equals(data)){
                TreeNode needed = children.contains(data.charAt(0));
                if (needed != null){
                    needed.insert(data.substring(1));
                }else{
                    children.add(new TreeNode(data.charAt(0)));
                    children.contains(data.charAt(0)).insert(data.substring(1));
                }
            }
        }
        public void recursionDraw(Integer stage){
            System.out.print("->");
            System.out.print(value);
            for (int i = 0; i < children.size(); i++){
                if(i > 0){
                    System.out.println();
                    for (int j = 0; j < stage; j++){
                        System.out.print("   ");
                    }
                    System.out.print("  " + value);
                }
                children.get(i).recursionDraw(stage + 1);
            }
        }
        public boolean contains(String data){
            if ("".equals(data)){
                return true;
            }else{
                TreeNode needed = children.contains(data.charAt(0));
                if (needed == null){
                    return false;
                }else{
                    return needed.contains(data.substring(1));
                }
            }
        }
        public void getAllNumbers(String text, List<String> res){
            String path = text + value;
            if (children.size() == 0){
                res.add(path);
            }else{
                for (TreeNode child: children){
                    child.getAllNumbers(path, res);
                }
            }
        }
        public void write(PrintWriter writer){
            writer.write(value);
            for (TreeNode child: children){
                child.write(writer);
            }
            writer.write("]");
        }
        public void read(FileReader reader) throws IOException {
            Stack<TreeNode> stack = new Stack<>();
            this.value = (char) reader.read();
            stack.add(this);
            char ch;
            while ((ch = (char)reader.read()) != '\uFFFF'){
                if (ch == ']' && stack.size() > 1){
                    TreeNode child = stack.pop();
                    stack.peek().children.add(child);
                }else{
                    stack.push(new TreeNode(ch));
                }
            }
            System.out.println(" ");
        }
    }

    public static void writeToFile(String path, TreeNode root){
        try {
            PrintWriter pw = new PrintWriter(new File(path));
            root.write(pw);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void readFromFile(String path, TreeNode root){
        try{
            FileReader reader = new FileReader(new File(path));
            root.read(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static class MyArrayList extends ArrayList<TreeNode>{
        public TreeNode contains(char value){
            for (int i = 0; i < size(); i++){
                TreeNode check = get(i);
                if (check.value == value){
                    return check;
                }
            }
            return null;
        }
    }
}
