package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.util.NoDataException
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
class QapitalLocalDataSource @Inject constructor(
    private val goalsDao: GoalsDao
) : LocalDataSource {

    override fun getSavingsGoals(): Single<List<SavingsGoal>> =
        Single.create { singleSubscriber ->
            val goals = goalsDao.getGoals()
            if (goals.isEmpty()) {
                singleSubscriber.onError(NoDataException())
            } else {
                singleSubscriber.onSuccess(goals)
            }
        }

    override fun insertAll(savingsGoals: List<SavingsGoal>) {
        goalsDao.insertAll(*savingsGoals.toTypedArray())
    }
}