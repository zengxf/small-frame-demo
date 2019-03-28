package cn.zxf.assertj;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class Assertions {

  /**
   * Creates a new instance of <code>{@link cn.zxf.assertj.TestUserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static cn.zxf.assertj.TestUserAssert assertThat(cn.zxf.assertj.TestUser actual) {
    return new cn.zxf.assertj.TestUserAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link cn.zxf.assertj.UserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static cn.zxf.assertj.UserAssert assertThat(cn.zxf.assertj.User actual) {
    return new cn.zxf.assertj.UserAssert(actual);
  }

  /**
   * Creates a new <code>{@link Assertions}</code>.
   */
  protected Assertions() {
    // empty
  }
}
