package kpd.animewatcher.model.repository

import kpd.animewatcher.model.repository.localRepository.ILocalRepository
import kpd.animewatcher.model.repository.remoteRepository.IRemoteRepository
import kpd.animewatcher.model.repository.remoteRepository.Result

class RepositoryImpl(
    private val localRepository: ILocalRepository,
    private val remoteRepository: IRemoteRepository
) : IRepository {
    override suspend fun getAnimeList(page: Int): Result = remoteRepository.loadAnime(page)
}