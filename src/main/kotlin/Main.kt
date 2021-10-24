import com.kamedon.ktestcase.testSuite

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
    println(suite.toString())
}
