# YAGNI: delete speculative options, in Kotlin

> Delete the settings nobody uses, so each new column is a two-line change.

[Watch the video](https://www.youtube.com/watch?v=CgHUtTPSuwE) · [Step through it interactively](https://illbebackend.dev/lessons/yagni-delete-speculative-options/kotlin/)

## The problem

The warehouse wants the postal code as a third column. The header and all three branches of the when on format change.

## Steps

Each folder holds the complete `OrderExport.kt` after that step, and every one compiles. Diff two neighbouring folders to see a single refactoring.

| Step | What changes | Move |
|---|---|---|
| [01](step-01-an-export-with-three-settings/) | An export with three settings: OrderExport reads a format, a delimiter and a header flag from application.yml. NightlyJob uploads the result to the warehouse. | Before |
| [02](step-02-one-column-four-edits/) | One column, four edits: The warehouse wants the postal code as a third column. The header and all three branches of the when on format change. | Code smell |
| [03](step-03-two-formats-nobody-reads/) | Two formats nobody reads: The config says csv and the only caller uploads CSV. The JSON and XML branches never run. | Code smell |
| [04](step-04-back-it-out-then-delete/) | Back it out, then delete: The postal code comes out. We remove the options nobody uses first, and add the column afterwards. | Plan |
| [05](step-05-delete-the-xml-branch/) | Delete the XML branch: No config value picks xml and no caller reads it, so the branch goes. | Remove Dead Code |
| [06](step-06-delete-the-json-branch/) | Delete the JSON branch: JSON goes for the same reason. The when is down to csv and an else that throws. | Remove Dead Code |
| [07](step-07-format-can-only-be-csv/) | Format can only be csv: The format property goes. The when collapses to the CSV row, the header test only checks header, and format leaves the config. | Remove Field |
| [08](step-08-the-delimiter-is-always-a-comma/) | The delimiter is always a comma: The comma goes straight into the header and the row. The delimiter property and its config line go. | Remove Field |
| [09](step-09-the-header-is-always-on/) | The header is always on: header is always true, so the branch that skips it is dead code. The if goes, then the property and its config line. | Remove Field |
| [10](step-10-nothing-left-to-configure/) | Nothing left to configure: The cfg parameter, ExportConfig and the job's config property go. The export section leaves application.yml. | Remove Parameter |
| [11](step-11-the-postal-code-in-two-edits/) | The postal code, in two edits: One new name in the header and one new value in the row. The loop, the return and NightlyJob didn't change. | Payoff |
| [12](step-12-a-builder-you-keep-naming/) | A builder you keep naming: out appears four times, each newline is typed by hand, and toString only turns the builder back into text. | Kotlin bonus |
| [13](step-13-let-buildstring-hold-the-builder/) | Let buildString hold the builder: buildString gives the lambda a StringBuilder as its receiver and returns the string. appendLine adds the newline. | buildString |

## Takeaways

- **Use it when:** An option has one value in the config and no caller uses the other branches.
- **Skip it when:** A second user is real and dated, like a signed contract for a JSON partner, or ops change the value per environment.
- **Spot it by:** Config values that never change, branches no caller reaches, defaults no caller overrides.
