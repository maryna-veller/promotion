package com.task.promotion

import scala.collection.mutable.ListBuffer

object Orchestrator {

  def allCombinablePromotions(allPromotionsWrapper: AllPromotions): Seq[PromotionCombo] = {
    val allPromotions = allPromotionsWrapper.promotionsMapped
    var promotionCombos = new ListBuffer[PromotionCombo]
    var nextRoot = Option(allPromotions.head._1)
    do {
      val current = allPromotions.get(nextRoot.get).orNull
      val runPromotionCodes = combinablePromotions(current.promotion, allPromotions.values.toSeq)
      promotionCombos ++= runPromotionCodes
      nextRoot = current.notCombinableWith.find((c: String) => c.compareTo(current.promotion) > 0)
    } while (nextRoot.isDefined)
    promotionCombos.toList
  }

  def combinablePromotions(promotionCode: String, allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
    val accumulated = new ListBuffer[PromotionCombo]()
    selectCompatible(promotionCode, allPromotions, Seq[String](), accumulated)
    accumulated.toList
  }

  private def selectCompatible(promotionCode: String, allPromotions: Seq[Promotion], selected: Seq[String], accumulated: ListBuffer[PromotionCombo]): Unit = {
    val newSelected = selected :+ promotionCode
    val compatible = allPromotions.filter((p: Promotion) => !(p.promotion == promotionCode)).filter(p => p.applicable(promotionCode))
    if (compatible.isEmpty && newSelected.size > 1 && !accumulated.exists(a => a.contains(newSelected))) accumulated += PromotionCombo(newSelected)

    compatible.indices.foreach((index: Int) => {
      def verifyAndRun(index: Int) = {
        val candidate = compatible(index)
        selectCompatible(candidate.promotion, compatible.slice(index + 1, compatible.size), newSelected, accumulated)
      }
      verifyAndRun(index)
    })
  }

}
