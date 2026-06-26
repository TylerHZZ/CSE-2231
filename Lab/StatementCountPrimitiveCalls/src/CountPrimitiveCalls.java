import components.statement.Statement;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;

        switch (s.kind()) {
            case BLOCK: {
                int i = 0;
                while (i < s.lengthOfBlock()) {
                    Statement nested = s.removeFromBlock(i);

                    count += countOfPrimitiveCalls(nested);

                    s.addToBlock(i, nested);
                    i++;
                }

                break;
            }

            case IF: {
                Statement body = s.newInstance();

                Statement.Condition c = s.disassembleIf(body);

                count = countOfPrimitiveCalls(body);

                s.assembleIf(c, body);

                break;
            }

            case IF_ELSE: {
                Statement thenBody = s.newInstance();
                Statement elseBody = s.newInstance();

                Statement.Condition c = s.disassembleIfElse(thenBody, elseBody);

                count = countOfPrimitiveCalls(thenBody)
                        + countOfPrimitiveCalls(elseBody);

                s.assembleIfElse(c, thenBody, elseBody);

                break;
            }

            case WHILE: {
                Statement body = s.newInstance();

                Statement.Condition c = s.disassembleWhile(body);

                count = countOfPrimitiveCalls(body);

                s.assembleWhile(c, body);

                break;
            }

            case CALL: {
                String instruction = s.disassembleCall();

                if (instruction.equals("move")
                        || instruction.equals("turnleft")
                        || instruction.equals("turnright")
                        || instruction.equals("infect")
                        || instruction.equals("skip")) {
                    count = 1;
                }

                s.assembleCall(instruction);

                break;
            }

            default: {
                break;
            }
        }

        return count;
    }

}
