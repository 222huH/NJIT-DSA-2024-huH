package oy.tol.tra;


public class ParenthesisChecker {

   private ParenthesisChecker() {
   }

      public static int checkParentheses(StackInterface<Character> stack, String fromString) throws ParenthesesException {
      int count=0;
      // for each character in the input string
      for (int i = 0; i < fromString.length(); i++) {
         char ch = fromString.charAt(i);

         // if character is an opening parenthesis -- one of "([{"
         if (ch == '(' || ch == '[' || ch == '{') {

            // push it into the stack (check for failure and throw an exception if so)
            if(stack==null){
               throw new ParenthesesException("stack failure", ParenthesesException.STACK_FAILURE);
            }
            stack.push(ch);
            count++;
            // else if character is a closing parenthesis -- one of ")]}"
         } else if (ch == ')' || ch == ']' || ch == '}') {
            if(stack.isEmpty()){
               throw new ParenthesesException("Too many closing parentheses.",ParenthesesException.TOO_MANY_CLOSING_PARENTHESES);
            }
            // pop the latest opening parenthesis from the stack
            Character popped = stack.pop();
            // if the popped item is null
            // throw an exception, there are too many closing parentheses
            count++;
            // check the popped opening parenthesis against the closing parenthesis read
            // from the string
            // if they do not match -- opening was { but closing was ], for example.
            // throw an exception, wrong kind of parenthesis were in the text (e.g. "asfa (
            // asdf } sadf")
            if ((ch == ')' && popped != '(') ||
                  (ch == ']' && popped != '[') ||
                  (ch == '}' && popped != '{')) {
               throw new ParenthesesException("Mismatched parentheses.",ParenthesesException.PARENTHESES_IN_WRONG_ORDER);
            }
         }
      }
      // if the stack is not empty after all the characters have been handled
      // throw an exception since the string has more opening than closing
      // parentheses.
      if (!stack.isEmpty()) {
         throw new ParenthesesException("Too many opening parentheses.",ParenthesesException.STACK_FAILURE);
      }

      return count;
   }
}