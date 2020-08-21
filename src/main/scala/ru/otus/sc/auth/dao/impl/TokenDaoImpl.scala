package ru.otus.sc.auth.dao.impl

import java.security.MessageDigest

import ru.otus.sc.auth.dao.TokenDao

class TokenDaoImpl extends TokenDao {

  private lazy val md5 = MessageDigest.getInstance("MD5")

  override def generateToken(str: String): String = {
    md5.update(str.getBytes())

    new String(md5.digest)
  }

}
