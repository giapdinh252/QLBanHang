/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.service;

import com.mycompany.qlbh.Dao.UsersDao;
import com.mycompany.qlbh.Dao.UsersDaolmpl;
import com.mycompany.qlbh.model.Users;

/**
 *
 * @author ADMIN
 */
public class UsersServiceImpl implements UsersService{
    private UsersDao usersDao = null;

    public UsersServiceImpl() {
        usersDao = new UsersDaolmpl();
    }

    @Override
    public Users login(String tenDangNhap, String password) {
        return usersDao.login(tenDangNhap, password);
    }
}
