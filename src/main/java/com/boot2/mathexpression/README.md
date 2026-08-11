# Math Expression Evaluator

Interview-style solution: parse a math expression string and return a `double`,
respecting operator precedence and parentheses.

## Supported

- Operators: `+`, `-`, `*`, `/`
- Parentheses: `(`, `)`
- Decimals: `12.5`
- Unary minus: `-5 + 10`
- Whitespace

## Precedence

1. Parentheses
2. `*` and `/` (left to right)
3. `+` and `-` (left to right)

## Approach

Two-stack algorithm (common interview answer):

1. Scan the expression left to right.
2. Push numbers onto a **values** stack.
3. Push operators onto an **operators** stack.
4. When a new operator arrives, apply higher/equal precedence operators first.
5. On `)`, apply operators until matching `(`.
6. At the end, apply remaining operators.

## Example

| Expression | Result |
|---|---|
| `3 + 4 * 2` | `11.0` |
| `(3 + 4) * 2` | `14.0` |
| `((2 + 3) * (4 - 1)) / 5` | `3.0` |

## Run

```bash
mvn -q exec:java -Dexec.mainClass=com.boot2.mathexpression.MathExpressionEvaluatorDemo
```

Or run `MathExpressionEvaluatorDemo` from the IDE.
