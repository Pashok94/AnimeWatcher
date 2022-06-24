package kpd.animewatcher.model.models.animeModel.saga

data class Saga(
    val descriptions: SagaDescriptions,
    val episode_from: Int,
    val episode_to: Int,
    val episodes_count: Int,
    val sagaTitles: SagaTitles
)