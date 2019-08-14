import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//public class Solution {
//    public List<String> findAllConcatenatedWordInADict(String[] words) {
//        List<String> result = new ArrayList<>();
//        for (int i = 0; i < words.length; i++) {
//            if (isConcat(words[i], words)) {
//                result.add(words[i]);
//            }
//        }
//        return result;
//    }
//
//    private boolean isConcat(String word, String[] words) {
//        Stack<String> st = new Stack<>();
//        for (int i = 0; i < words.length; i++) {
//            if (word.indexOf(words[i]) != -1 && !word.equals(words[i])) {
//                st.push(words[i]);
//            }
//        }
//        while (!st.isEmpty()) {
//            String curr = st.pop();
//            String rest = word.substring(curr.length());
//            for (int i = 0; i < words.length; i++) {
//                if (rest.indexOf(words[i]) != -1) {
//                    if (rest.equals(words[i])) {
//                        return true;
//                    }
//                    else {
//                        st.push(curr+words[i]);
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    public static void main(String[] args) {
//        String[] words = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
//
//        Solution soln = new Solution();
//        List<String> result = soln.findAllConcatenatedWordInADict(words);
//        result.forEach(System.out::println);
//    }
//
//}


public class Solution {
    public List<String> findAllConcatenatedWordInADictl(String[] words) {
        TrieNode root = buildTrie(words);
//        print(root);
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (isConcat(root, word)) {
                result.add(word);
            }
        }
        return result;
    }

    private boolean isConcat(TrieNode root, String word) {
        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair<String>(word, root));
        int sz = stack.size();
        int lvl = 0;
        while (!stack.isEmpty()) {
            Pair curr = stack.pop();
            sz--;
            if (curr.str.equals(curr.node.str) && lvl > 1) {
                return true;
            } else {
                if (((String)curr.str).indexOf(curr.node.str) == 0) {
                    String rest = ((String)curr.str).substring(curr.node.str.length());
                    for (int i = 0; i < curr.node.children.size(); i++) {
                        if (rest.indexOf(curr.node.children.get(i).str) == 0) {
                            stack.push(new Pair(rest, curr.node.children.get(i)));
                        }
                    }
                }
            }
            if (sz == 0) {
                lvl++;
                sz = stack.size();
            }
        }
        return false;
    }

    class TrieNode {
        public String str;
        public List<TrieNode> children;

        public TrieNode(String str) {
            this.str = str;
            this.children = new ArrayList<>();
        }
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode("");
		for (int i= 0; i < words.length; i++) {
            root.children.add(new TrieNode(words[i]));
        }
//		TrieNode curr;
//		for (int i = 0;  i < words.length; i++) {
//		    curr = root;
//		    if (words[i].startsWith(curr.str)) {
//		        String rest = words[i].substring(curr.str.length());
//		        for (int j = 0; j < curr.children.size(); j++) {
//		            if (rest.startsWith(curr.children.get(j).str)) {
//		                curr = curr.children.get(j);
//                    }
//                }
//            }
//        }
        for (String word : words) {
            addToTrie(root, word, words);
        }
        return root;
    }

    class Pair <T> {
        public T str;
        public TrieNode node;

        public Pair(final T str, final TrieNode node) {
            this.str = str;
            this.node = node;
        }
    }

    private void addToTrie(TrieNode root, String currWord, String[] words) {
        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair<String>(currWord, root));
        while (!stack.isEmpty()) {
            Pair curr = stack.pop();
            String rest = ((String)curr.str).substring(curr.node.str.length());
            if (!curr.node.children.isEmpty()) {
                for (TrieNode n : curr.node.children) {
                    if (rest.indexOf(n.str) == 0) {
                        stack.push(new Pair<String>(rest, n));
                    }
                }
            } else {
                for (String word : words) {
                    if (rest.indexOf(word) == 0) {
                        TrieNode n = new TrieNode(word);
                        curr.node.children.add(n);
                        stack.push(new Pair<String>(rest, n));
                    }
                }
            }
        }
    }

    void print(TrieNode root) {
        if (root != null) {
            if (!root.children.isEmpty()) {
                System.out.print(root.str + ": ");
                root.children.forEach(n -> System.out.print(n.str + " "));
                System.out.println();
                root.children.forEach(n -> print(n));
            } else {
                System.out.println(root.str + ": null");
            }
        }
    }

    public static void main(String[] args) {
        String[] words = {"cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat"};

        Solution soln = new Solution();
        List<String> cat = soln.findAllConcatenatedWordInADictl(words);
        System.out.println("The concatenated words are:");
        if (!cat.isEmpty()) {
            cat.forEach(s->System.out.print(s+" "));
        }
        System.out.println();
    }

}
