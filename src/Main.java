import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        /* Inserting elements */
        tree.insert(10, "Ten");
        tree.insert(20, "Twenty");
        tree.insert(30, "Thirty");
        tree.insert(40, "Forty");
        tree.insert(50, "Fifty");
        tree.insert(25, "Twenty-Five");
        tree.insert(60, "Sixty");
        tree.insert(70, "Seventy");
        tree.insert(80, "Eighty");
        tree.insert(90, "Ninety");

        System.out.println("Initial tree (pre-order traversal): ");
        preOrder(tree.getRoot());
        System.out.println();
        System.out.println("Initial tree (visualized): ");
        printTreeVertically(tree.getRoot());
        System.out.println();

        /* Deleting elements */
        System.out.println("Deleting node 50...");
        tree.delete(50);
        System.out.println("Tree after deleting node 50 (pre-order traversal): ");
        preOrder(tree.getRoot());
        System.out.println();
        System.out.println("Tree after deleting node 50 (visualized): ");
        printTreeVertically(tree.getRoot());
        System.out.println();

        System.out.println("Deleting node 10...");
        tree.delete(10);
        System.out.println("Tree after deleting node 10 (pre-order traversal): ");
        preOrder(tree.getRoot());
        System.out.println();
        System.out.println("Tree after deleting node 10 (visualized): ");
        printTreeVertically(tree.getRoot());
    }

    public static void preOrder(AVLTree.IAVLNode node) {
        if (node != null && node.isRealNode()) {
            System.out.print(node.getKey() + " ");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    public static void printTreeVertically(AVLTree.IAVLNode root) {
        List<List<String>> lines = new ArrayList<>();
        List<AVLTree.IAVLNode> level = new ArrayList<>();
        List<AVLTree.IAVLNode> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();

            nn = 0;

            for (AVLTree.IAVLNode n : level) {
                if (n == null || !n.isRealNode()) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = String.valueOf(n.getKey());
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null && n.getLeft().isRealNode()) nn++;
                    if (n.getRight() != null && n.getRight().isRealNode()) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<AVLTree.IAVLNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
}
