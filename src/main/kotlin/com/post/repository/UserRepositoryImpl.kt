package com.post.repository

import com.post.db.data.post.Posts
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import com.post.db.data.user.Users
import com.post.db.data.user.toUser
import com.post.db.dbQuery
import com.post.dto.user.UserResponseDto
import com.post.model.UserBadge
import com.post.model.UserModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.update

class UserRepositoryImpl : UserRepository {

    override suspend fun getById(id: Long): UserModel? =
        dbQuery {
            Users.select {
                (Users.id eq id)
            }.singleOrNull()?.toUser()
        }

    override suspend fun getByIds(ids: Collection<Long>): List<UserModel> =
        dbQuery {
            Users.select {
                (Users.id inList ids)
            }.map {
                it.toUser()
            }
        }

    override suspend fun getByUsername(username: String): UserModel? =
        dbQuery {
            Users.select {
                (Users.username eq username)
            }.singleOrNull()?.toUser()
        }

    override suspend fun save(item: UserModel) {
        dbQuery {
            Users.insert { insertStatement ->
                insertStatement[password] = item.password
                insertStatement[username] = item.username
                insertStatement[image_id] = item.imageId
                insertStatement[badge] = item.badge?.name
                insertStatement[not_approve] = item.notApprove
                insertStatement[approve] = item.approve
                insertStatement[only_reads] = item.onlyReads
                insertStatement[firebase_id] = item.firebaseId
            }
        }
    }

    override suspend fun update(id: Long, item: UserResponseDto) {
        dbQuery {
            Users.update(
                where = {
                    Users.id eq id
                },
                body = { updateStatement ->
                    updateStatement[username] = item.username
                    updateStatement[image_id] = item.imageId
                    updateStatement[badge] = item.badge?.name
                    updateStatement[not_approve] = item.notApprove
                    updateStatement[approve] = item.approve
                    updateStatement[only_reads] = item.onlyReads
                    updateStatement[firebase_id] = item.firebaseId
                }
            )
            val user = Users.select { Users.id eq id }.singleOrNull()?.toUser()
            user?.let {
                if (it.approve > 100) {
                    Users.update(
                        where = {
                            Users.id eq id
                        },
                        body = { updateStatement ->
                            updateStatement[badge] = UserBadge.PROMOTER.name
                        }
                    )
                }

                if (user?.notApprove!! > 100) {
                    Users.update(
                        where = {
                            Users.id eq id
                        },
                        body = { updateStatement ->
                            updateStatement[badge] = UserBadge.HATER.name
                        }
                    )
                }
            }


        }
    }


}

