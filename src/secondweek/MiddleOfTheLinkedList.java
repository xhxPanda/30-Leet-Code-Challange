package secondweek;

public class MiddleOfTheLinkedList {
    public ListNode middleNode(ListNode head) {
        ListNode current = head;
        int c = 0;
        while (current != null) {
            c++;
            current = current ;
        }
        c /= 2;
        while (c > 0) {
            c--;
            head = head.next;
        }
        return head;
    }

    public static void main(String[] args) {
        System.out.println(100%3);
    }
}



