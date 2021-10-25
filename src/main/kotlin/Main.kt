import com.kamedon.ktestcase.*

fun main(args: Array<String>) {
    val suite = testSuite("ログイン画面") {
        case("ログインできること") {
            preCondition {
                condition("未ログイン状態であること")
            }
            step("https://xxxxx.com にアクセスする")
            step("ユーザ名を「Kamedon」と入力する")
            step("パスワードを「Password」と入力する")
            step("ログインをクリックする")
            verify("「ログインしました」とトースト通知されること")
            postCondition {
                condition("ログイン済み状態であること")
            }
        }
        case("入力が不正のときはログインができないこと") {
            preCondition {
                condition("未ログイン状態であること")
            }
            step("https://xxxxx.com にアクセスする")
            step("ユーザ名を「Kamedon」と入力する")
            step("ログインをクリックする")
            verify("「パスワードが未入力です」とトースト通知されること")
            postCondition {
                condition("未ログイン状態であること")
            }
        }
    }
    println(suite.markdown())
}

fun TestSuite.markdown(): String {
    fun suiteTitle() = "# ${title}\n"
    fun TestCase.title() = "## ${title}\n"

    fun preConditionTitle() = "### 事前条件\n"
    fun postConditionTitle() = "### 事後条件\n"
    fun verifyTitle() = "### 期待結果\n"
    fun stepTitle() = "### テスト手順\n"

    fun TestCaseCondition.title() = "- $title\n"

    fun TestCaseStep.title(index: Int) = "${index}. ${title}\n"
    fun TestCaseVerify.title() = "- [ ] $title\n"

    fun TestCaseStep.markdown(index: Int): String {
        return title(index) + verifies.joinToString("") { caseStepVerify ->
            "    ${caseStepVerify.title()}"
        }
    }


    fun TestAttribute.markdown() = when (this) {
        TestAttribute.NONE -> ""
        is TestAttribute.Attribute<*> -> {
            "### Attribute \n" + (value as? Map<*, *>)?.let {
                it.entries.joinToString("") { entry ->
                    "- ${entry.key}: ${entry.value}\n"
                }
            }
        }

    }

    fun TestCase.markdown(): String {

        val preConditionMarkdown = preConditionTitle() + preConditions.conditions.joinToString("") { it.title() } + "\n"
        val postConditionMarkdown =
            postConditionTitle() + postConditions.conditions.joinToString("") { it.title() }
        val verifyMarkdown = verifyTitle() + verifies.joinToString("") { it.title() }
        val attributeMarkdown = attribute.markdown()

        val stepMarkdown =
            stepTitle() + caseSteps.mapIndexed { index, caseStep ->
                caseStep.markdown(index + 1)
            }.joinToString("\n")

        return title() + attributeMarkdown + preConditionMarkdown + stepMarkdown + verifyMarkdown + postConditionMarkdown
    }

    return suiteTitle() + attribute.markdown() + cases.joinToString("\n") { it.markdown() }
}
