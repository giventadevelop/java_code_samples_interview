# Transaction Matching

Compare **internal** and **partner** transaction lists and report:

1. **Matched** – same transaction key appears in both lists
2. **Unmatched / distinct** – present in only one list
3. **Duplicates** – same key appears more than once within a list

## Match key

`Transaction.equals` / `hashCode` use **id + amount**. Timestamp is kept for display only.

## Classes

| Class | Role |
|---|---|
| `Transaction` | Transaction record |
| `TransactionMatcher` | Matching / duplicate logic |
| `TransactionMatchResult` | Holds the three result buckets |
| `TransactionMatchingDemo` | Sample run |

## Run

```bash
mvn -q compile
java -cp target/classes com.boot2.transactionmatching.TransactionMatchingDemo
```
