package com.convenient.excel;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }


    public static void main(String[] args) {
        TreeNode node = new TreeNode(15);
        node.left = new TreeNode(12);
        node.right = new TreeNode(20);
        node.left.left = new TreeNode(8);
        node.left.right = new TreeNode(10);
        node.invertTree(node);
        System.out.println("------------------------------------>" + (5 & 7));
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        invertTreeLeft(root);
        return root;
    }

    public TreeNode invertTreeLeft(TreeNode root) {
        if (root == null) {
            return root;
        }
        invertTreeLeft(root.left);
        invertTreeLeft(root.right);
        TreeNode right = root.right;
        root.right = root.left;
        root.left = right;
        return root;
    }
}