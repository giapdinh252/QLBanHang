/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.Dao;

import com.mycompany.qlbh.model.Users;

/**
 *
 * @author ADMIN
 */
public interface UsersDao {
    
    public Users login(String tenDangNhap, String password);
    
}
