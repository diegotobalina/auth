package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.events.ports.PublishUpdatedPasswordEventPort;
import com.spring.auth.events.publishers.UpdatedPasswordEventPublisher;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.user.application.ports.in.UpdateUserPasswordPort;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.application.ports.out.UpdateUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class UpdateUserPasswordUseCase implements UpdateUserPasswordPort {

  private UpdateUserPort updateUserPort;
  private FindUserPort findUserPort;
  private PublishUpdatedPasswordEventPort publishUpdatedPasswordEventPort;

  /**
   * @param user User which needs the password update
   * @param oldPassword Old password for the password check before the update
   * @param newPassword New password for the user
   * @return Updated user
   * @throws DuplicatedKeyException If there is some problem saving the user for duplications, like
   *     username
   * @throws WrongPasswordException If the old password is not correct
   */
  @Override
  public User update(User user, String oldPassword, String newPassword)
      throws DuplicatedKeyException, WrongPasswordException {
    if (!user.doPasswordsMatch(oldPassword))
      throw new WrongPasswordException("invalid old password");
    return update(user, newPassword);
  }

  /**
   * Update the user password and save it in the database
   *
   * @param user User which needs the password update
   * @param password New password for the user
   * @return Updated user
   * @throws DuplicatedKeyException If there is some problem saving the user for duplications, like
   *     username
   */
  @Override
  public User update(User user, String password) throws DuplicatedKeyException {
    user.updatePassword(password);
    User updatedUser = updateUserPort.update(user);
    publishUpdatedPasswordEventPort.publish(updatedUser);
    return updatedUser;
  }

  /**
   * Update user password and save it in the databsae
   *
   * @param userId Find the user using the userId
   * @param oldPassword Old password for the password check before the update
   * @param newPassword New password for the user
   * @throws NotFoundException If the user was not found with the id
   * @throws DuplicatedKeyException If the user have some problems while saving it like duplicated
   *     username
   * @throws WrongPasswordException If the old password is not correct
   */
  @Override
  public void update(String userId, String oldPassword, String newPassword)
      throws NotFoundException, DuplicatedKeyException, WrongPasswordException {
    User user = findUserPort.findById(userId);
    update(user, oldPassword, newPassword);
  }

  /**
   * Update the user password and save it in the databsae
   *
   * @param userId Find the user using the userId
   * @param newPassword Set the new user password with this param
   * @throws NotFoundException If the user was not found
   * @throws DuplicatedKeyException If the user have some problems while saving it like duplicated
   *     username
   */
  @Override
  public void update(String userId, String newPassword)
      throws NotFoundException, DuplicatedKeyException {
    User user = findUserPort.findById(userId);
    update(user, newPassword);
  }
}
