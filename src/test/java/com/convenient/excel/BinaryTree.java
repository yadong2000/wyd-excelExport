package com.convenient.excel;

import java.util.HashMap;
import java.util.Map;

/*** public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 * */
public class BinaryTree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public static void main(String[] args) {
        TreeNode node = new TreeNode(15);
        node.left = new TreeNode(12);
        node.right = new TreeNode(20);
        node.left.left = new TreeNode(8);
        node.left.right = new TreeNode(10);
        invertTree(node);
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        invertTreeLeft(root);
        return root;
    }

    public static TreeNode invertTreeLeft(TreeNode root) {
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
