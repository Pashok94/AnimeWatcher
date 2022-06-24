package kpd.animewatcher.model.repository

import kpd.animewatcher.model.repository.remoteRepository.Result

interface IRepository {
    suspend fun getAnimeList(page: Int): Result
}