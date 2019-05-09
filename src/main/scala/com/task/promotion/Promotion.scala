package com.task.promotion

case class Promotion(promotion: String, var notCombinableWith: Seq[String]) {
  require(notEmpty(promotion), "the promotion must be present")
  require(notCombinableWith == null || notCombinableWith.forall(p => notEmpty(p) && p != promotion), "the promotion must be present and not equal self")
  if (notCombinableWith == null) notCombinableWith = Seq()

  def applicable(potentialCombination: String): Boolean = !notCombinableWith.contains(potentialCombination)


  private def notEmpty(value: String): Boolean = {
    !Option(value).forall(_.trim.isEmpty)
  }
}
