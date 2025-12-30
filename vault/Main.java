package vault;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.InputMismatchException;
import java.util.Date;


class Main {
	static final String BLACK = "\u001B[30m";
	static final String RED = "\u001B[31m";
	static final String GREEN = "\u001B[32m";
	static final String YELLOW = "\u001B[33m";
	static final String BLUE = "\u001B[34m";
	static final String MAGNETA = "\u001B[35m";
	static final String CYAN = "\u001B[36m";
	static final String WHITE = "\u001B[37m";
	static Vault vault= new Vault();
	private static final Logger logger=LogManager.getLogger();

	
	public static void main(String[] arr) {
		logger.info("Main Thread Started");
		Scanner sc = new Scanner(System.in);
		System.out.println(MAGNETA
				+ "-----------------------------------------------------------\n-------------Hii Welcome to ZS Password Manager------------\n-----------------------------------------------------------\n"
				+ WHITE);
		boolean login = false;
		int loginId = -1;
		try {
			System.out.print("1. Already a Customer? Login \t 2.SignUp \n\nEnter Your Option : ");
			int option = sc.nextInt();
			sc.nextLine();
			logger.info("Current User is going to login");
			if (option == 1) {
				while (true) {
					System.out.print("\nEnter Your User Name : ");
					String userName = sc.nextLine().toLowerCase();
					System.out.print("Enter your password : ");
					String password = sc.nextLine();
					loginId = vault.login(userName, password);
					if (loginId > 0) {
						login = true;
						logger.info("Login Successful loginId : "+loginId);
						break;
					} else {
						System.out.println(RED
								+ "\n------Incorrect userName or password------------\nWant to retry y/n ??\n" + WHITE);
						logger.info("Login Failed ");
						char ans = sc.nextLine().toLowerCase().charAt(0);
						if (ans == 'y') {
							continue;
						}
					}
					System.out.println(
							BLUE + "\n------No User Found with given Username SignUp ! y/n ??---------\n" + WHITE);
					char ans = sc.nextLine().toLowerCase().charAt(0);
					if (ans == 'y') {
						option = 2;
						break;
					}
					break;
				}
			}
			if (option == 2) {
				logger.info("Current User is going to signup ");
				System.out.print("Enter your Name : ");
				String name = sc.nextLine();
				String userName = "";
				do {
					System.out.print("Enter your userName : ");
					userName = sc.nextLine().toLowerCase();
					if (userName.length() <= 100 && userName.length() > 3) {
						if (!vault.userNameExist(userName)) {
							break;
						}
						System.out.println(RED + "/////////// UserName Already Exist ///////////" + WHITE);
					} else {
						System.out.println(
								BLUE + "Username must be within 100 characters and minimum 4 characters" + WHITE);
					}
				} while (true);
				String password = "";
				do {
					System.out.print("Enter Your password : ");
					password = sc.nextLine();
					if (password.length() > 7 && password.length() <= 100) {
						break;
					}
					System.out
							.println(BLUE + "Password must be minimum 8 characters and maximum 100 characters" + WHITE);
				} while (true);
				System.out.println(GREEN + "......Creating Account ............" + WHITE);
				int userId = vault.addUser(name, userName, password, 2);
				if (userId != 0) {
					RSA rsa = new RSA();
					vault.addCustomer(userId, rsa.n, rsa.e, rsa.d, (byte) 0);
					login = true;
					loginId = userId;
					logger.info("SignUp Successful loginId : "+loginId);
				} else {
					System.out.println(RED + "///////////Error occured Try again later //////////////" + WHITE);
				}
			} else if (option != 1 && option != 2) {
				System.out.println(RED + "///////////Invalid Option//////////////" + WHITE);
			}
			if (login) {
				vault.addAudit(1, loginId);
				if (vault.getRole(loginId) == 1) {
					System.out.println(MAGNETA + "Welcome Admin "+vault.getName() + WHITE);
					logger.info("Current User is Admin");
					option = 0;
					while (option != 5) {
						System.out.println(BLUE + "--------------- Main Menu ---------------" + WHITE);
						logger.info(loginId+" entered Admin Menu");
						System.out.println(
								"1.View All Users \n2.Audit\n3.Add Admin\n4.Unlock Customer\n5.Exit\n\nEnter your option");
						option = sc.nextInt();
						if (option == 1) {
							vault.addAudit(2, loginId);
							vault.viewAllUser();
							logger.info(loginId+" Viewed all Users");
						} else if (option == 2) {
							int subOption = 0;
							logger.info("user  " +loginId+" Entered Audit Menu");
							while (subOption != 4) {
								System.out.println(BLUE + "------------ AUDIT MENU -----------" + WHITE);
								System.out.print(
										"1.View last 20 actions\n2.View audit of a certain user\n3.View audit of a action\n4.Back\n\nEnter the option : ");
								subOption = sc.nextInt();
								if (subOption == 1) {
									vault.viewAudit();
									logger.info("user "+loginId+" entered audit List");
									System.out.println(BLUE + "--------- End ---------" + WHITE);
								} else if (subOption == 2) {
									sc.nextLine();
									System.out.print("Enter the userName : ");
									String userName = sc.nextLine().toLowerCase();
									int result = vault.auditOfUser(userName);
									logger.info("user "+loginId+" Viewed audit of a user "+userName);
									if (result != -1) {
										System.out.println(BLUE + "\n------- END --------\n" + WHITE);
									} else {
										System.out.println(RED + "\n-------- USER NOT FOUND --------\n" + WHITE);
									}
								} else if (subOption == 3) {
									Audit.displayActions();
									System.out.print("\nEnter the no of action : ");
									int action = sc.nextInt();
									if (action > 0 && action <= Audit.arr.length) {
										vault.auditOfAction(action);
										logger.info("user "+loginId+" viewed auditList of actionId "+action);
										System.out.println(BLUE + "\n------- END --------\n" + WHITE);
									} else {
										System.out.println(RED + "\n-------- ACTION NOT FOUND --------\n" + WHITE);
									}
								} else if (subOption != 4) {
									System.out.println(RED + "--------- Invalid option -----------");
								}
							}
						} else if (option == 3) {
							sc.nextLine();
							System.out.print("Enter the admin name : ");
							String name = sc.nextLine();
							String userName = "";
							do {
								System.out.print("Enter admin User name : ");
								userName = sc.nextLine().toLowerCase();
								if (!vault.userNameExist(userName)) {
									break;
								}
								System.out.println(RED + "///////////UserName Already exist/////////////" + WHITE);
							} while (true);
							System.out.print("Enter the password : ");
							String password = sc.nextLine();
							vault.addUser(name, userName, password, 1);
							logger.info("user "+loginId+" add new admin");
							vault.addAudit(3, loginId);
							System.out.println(GREEN + "--------Admin added successfully --------------\n" + WHITE);
						} else if (option == 4) {
							sc.nextLine();
							System.out.print("Enter the userName : ");
							String userName = sc.nextLine();
							int result=vault.unlock(userName);
							if (result != -1) {
								if (result==1) {
									logger.info("user "+loginId+" unlocked customer "+userName);
									System.out.println(GREEN + "--------- UNLOCKED SUCCESSFULLY ---------" + WHITE);
								} else {
									System.out
											.println(GREEN + "---------- USER ACCOUNT IS NOT LOCKED ---------" + WHITE);
								}
							}

							else {
								System.out.println(RED + "----------USERNAME DOESN'T EXIST-----------");
							}
						} else if (option != 5) {
							System.out.println(RED + "/////////////////Invalid option/////////////////" + WHITE);
						}
					}
					System.out.println(
							MAGNETA + "--------------------- Logged Out Successfully -------------------------");
				} else {
					if (!vault.checkLockStatus(loginId)) {
						logger.info("Customer "+loginId+" viewed Main Menu");
						vault.storeCustomer(loginId);
						System.out.println(
								GREEN + "\nLogin Successfull Welcome : " + vault.getName() + "\n" + WHITE);
						option = 0;
						while (option != 7) {
							logger.info("Customer "+loginId+" viewed Main Menu");
							System.out.println(BLUE + "--------------- Main Menu ---------------" + WHITE);
							System.out.print(
									"1.Applications\n2.Share\n3.View Shared List\n4.View shared with me List\n5.Trash\n6.Update Profie\n7.Exit\n\nEnter Your Option : ");
							option = sc.nextInt();
							sc.nextLine();
							if (option == 1) {
								int subOption = 0;
								logger.info("User "+loginId+" viewed Application Menu");
								while (subOption != 7) {
									System.out
											.println(CYAN + "--------------- Application Menu ---------------" + WHITE);
									System.out.print(
											"\n\n1.Search a Application\n2.View All Application\n3.View password\n4.Add application\n5.Delete Application\n6.Update Application\n7.Get Back\n\nEnter your option : ");
									subOption = sc.nextInt();
									sc.nextLine();
									if (subOption == 1) {
										if (!vault.isCurrentCustomerHasApplication()) {
											System.out.print("\nEnter the Application you want to search : ");
											String application = sc.nextLine().toLowerCase();
											boolean result = vault.searchApplicationInCustomer(application);
											logger.info("Customer "+loginId+" search application "+application);
											vault.addAudit(4, loginId);
											if (result) {
												System.out.println(GREEN
														+ "----------Yes You have the application-----------\nWant to view it y/n  ??"
														+ WHITE);
												char choice = sc.nextLine().toLowerCase().charAt(0);
												if (choice == 'y') {
													vault.displayApplicationOfCustomer(application);
													logger.info("Customer "+loginId+" viewed application "+application);
													System.out.println("\n");
													vault.addAudit(5, loginId);
												}
											} else {
												System.out.println(RED + "\n///////////  You don't have " + application
														+ "!  ///////////\n" + WHITE);
											}
										} else {
											System.out.println(CYAN
													+ "------------ Application List is Empty -------------\n" + CYAN);
										}
									} else if (subOption == 2) {
										if (!vault.isCurrentCustomerHasApplication()) {
											vault.viewAllApplicationOfCustomer();
											vault.addAudit(6, loginId);
											logger.info("Customer "+loginId+" viewed All APplication ");
											System.out.println(BLUE + "\n------------End------------\n" + WHITE);
										} else {
											System.out.println(CYAN
													+ "------------ Application List is Empty -------------\n" + WHITE);
										}
									} else if (subOption == 3) {
										if (!vault.isCurrentCustomerHasApplication()) {
											System.out.println("\n");
											vault.viewNamesOfApplicationOfCustomer();
											System.out.println("\n");
											System.out.print("Enter the index you want to see password : ");
											int index = (sc.nextInt() - 1);
											logger.info("Customer "+loginId+" want to see password of index "+index);
											if (index > -1 && index < vault.totalNoOfApplication()) {
												int attempt = 0;
												sc.nextLine();
												while (attempt < 3) {
													System.out.println("\n");
													System.out.print("Enter your vault password to see password : ");
													logger.info("Customer "+loginId+" enter vault password");
													String password = sc.nextLine();
													attempt++;
													if (vault.checkPasswordOfCustomer(password)) {
														vault.viewPasswordOfApplication(index);
														logger.info("Customer "+loginId+" viewed password of the application ");
														vault.addAudit(7, loginId);
														System.out.println("\n");
														break;
													} else {
														System.out.println(RED
																+ "\n------------Invalid vault password--------------\n"
																+ WHITE);
													}
													if (attempt == 3) {
														System.out.println(RED
																+ "\n++++++ MAX ATTEMPT REACHED YOUR ACCOUNT IS LOCKED +++++++++\n"
																+ WHITE);
														logger.info("Customer "+loginId+" account Locked");
														vault.lockCustomer(loginId);
														subOption = 7;
														option = 7;
													}
												}

											} else {
												System.out.println(RED
														+ "\n//////////   Invalid Index   //////////////\n" + WHITE);
											}
										} else {
											System.out.println(CYAN
													+ "------------ Application List is Empty -------------\n" + WHITE);
										}
									} else if (subOption == 4) {
										System.out.println("\n");
										System.out.print("Enter the application Name : ");
										String appName = sc.nextLine().toLowerCase();
										System.out.print("Enter the userName : ");
										String userName = sc.nextLine();
										String password = "";
										do {
											System.out.print("Enter the password : ");
											password = sc.nextLine();
											if (password.length() > 7) {
												break;
											}
											System.out.println(BLUE
													+ "---------Password should have 8 characters -----------" + WHITE);
										} while (true);
										System.out.print("Enter description : ");
										String description = sc.nextLine();
										vault.addApplicationToCustomer(appName, userName,
												password, new Date(), description);
										vault.addAudit(8, loginId);
										logger.info("Customer "+loginId+" added new application "+appName);
										System.out.println(
												GREEN + "------------Application added successfully --------------\n"
														+ WHITE);
									} else if (subOption == 5) {
											if (!vault.isCurrentCustomerHasApplication()) {
												vault.viewNamesOfApplicationOfCustomer();
												System.out.print("Enter the index you want to delete : ");
												int index = (sc.nextInt() - 1);
												sc.nextLine();
												if (index > -1 && index < vault.totalNoOfApplication()) {
													System.out.println(
															"---------Type the reason you want to delete----------");
													String reason = sc.nextLine();
													System.out.println(BLUE
															+ "\nAre you sure ? You want to delete this application ? y/n ?"
															+ WHITE);
													char answer = sc.nextLine().toLowerCase().charAt(0);
													logger.info("Customer "+loginId+" deleted application "+index);
													if (answer == 'y') {
														vault.deleteApplication(index, reason);
														vault.addAudit(9, loginId);
														System.out.println(GREEN
																+ "-------- Application deleted successfully --------------\n"
																+ WHITE);
													} else {
														System.out.println(
																RED + " Deletion process Cancelled \n" + WHITE);
													}
												} else {
													System.out.println(RED
															+ "////////////Invalid Index for deleting//////////////\n"
															+ WHITE);
												}
											} else {
												System.out.println(
														CYAN + "------------ Application List is Empty -------------\n"
																+ WHITE);
											}
									} else if (subOption == 6) {
										if (!vault.isCurrentCustomerHasApplication()) {
											int attempt = 0;
											System.out.println("\n");
											while (attempt < 3) {
												System.out.println("Enter you vault password to update");
												String password = sc.nextLine();
												logger.info("Customer "+loginId+" entered update application module");
												attempt++;
												if (vault.checkPasswordOfCustomer(password)) {
													vault.addAudit(10, loginId);
													vault.viewNamesOfApplicationOfCustomer();
													System.out.print("Enter the application no you want to edit :");
													int appIndex = (sc.nextInt() - 1);
													if (appIndex > -1
															&& appIndex < vault.totalNoOfApplication()) {
														System.out.println(
																"You want to Edit \n1.application Name 2.UserName 3.Password");
														int choice = sc.nextInt();
														sc.nextLine();
														if (choice == 1) {
															System.out.print("Enter the new application name : ");
															String applicationName = sc.nextLine().toLowerCase();
//															currentCustomer.ownApplication.get(appIndex).updateApplicationName(applicationName);
															vault.updateApplicationOfCustomer( "name",
																	appIndex,
																	applicationName);
															logger.info("Customer "+loginId+" updated application name of "+appIndex);
															System.out.println(GREEN
																	+ "-------Updated successfully--------\n" + WHITE);
															vault.addAudit(11, loginId);
														} else if (choice == 2) {
															System.out.print("Enter the new userName : ");
															String userName = sc.nextLine();
															vault.updateApplicationOfCustomer("username",
																	appIndex,
																	userName);
															logger.info("Customer "+loginId+" updated application userName of "+appIndex);
															System.out.println(
																	GREEN + "----------Updated successfully----------\n"
																			+ WHITE);
															vault.addAudit(12, loginId);
														} else if (choice == 3) {
															System.out.print("Enter the new password : ");
															String newPassword = sc.nextLine();
															logger.info("Customer "+loginId+" updated  appliationpassword of  "+appIndex);
															vault.updateApplicationOfCustomer( "password",
																	appIndex,
																	newPassword);
															System.out.println(GREEN
																	+ "----------Updated successfully-----------\n"
																	+ WHITE);
															vault.addAudit(13, loginId);
														} else {
															System.out.println(
																	RED + "----- Invalid Choice ---------\n" + WHITE);
														}
													} else {
														System.out.println(
																RED + "---------- Invalid application Index ---------\n"
																		+ WHITE);
													}

													break;

												} else {
													System.out.println(
															RED + "----------- Wrong password Try again -----------"
																	+ WHITE);
												}
												if (attempt == 3) {
													System.out.println(RED
															+ "\n++++++++++++++++++++ MAX ATTEMPT REACHED ++++++++++++++++++\n");
													System.out.println(
															"\n------------------- YOU ACCOUNT IS LOCKED -----------------\n"
																	+ WHITE);
													logger.info("Customer "+loginId+" locked ");
													vault.lockCustomer(loginId);
													option = 7;
													subOption = 7;
												}
											}

										} else {
											System.out.println(CYAN
													+ "------------ Application List is Empty -------------\n" + WHITE);
										}
									} else if (subOption != 7) {
										System.out.println(RED + "------- Invalid Choice ------------\n" + WHITE);
									}
								}

							} else if (option == 2) {
								if (!vault.isCurrentCustomerHasApplication()) {
									System.out.println("\n");
									System.out.print("Enter the customer userName you want to share with : ");
									String cusName = sc.nextLine().toLowerCase();
									boolean customer = vault.isCustomer(cusName,loginId);
									if (customer) {
										vault.viewNamesOfApplicationOfCustomer();
										System.out.print("Enter the index of application you want to share : ");
										int appIndex = (sc.nextInt() - 1);
										if (appIndex > -1 && appIndex <vault.totalNoOfApplication()) {
											vault.shareToCustomer(cusName,
													appIndex);
											logger.info("Customer "+loginId+" shared application "+appIndex+" with customer "+cusName);
											vault.addAudit(14, loginId);
											System.out.println(GREEN
													+ "------------ Application shared successfully --------------\n"
													+ WHITE);
										} else {
											System.out.println(
													RED + "/////////// Invaid application index number ////////////\n"
															+ WHITE);
										}
									} else {
										System.out.println(BLUE + "///////////  User " + cusName
												+ " doesn't Exist  //////////////////\n" + WHITE);
									}
								} else {
									System.out.println(
											CYAN + "------------ Application List is Empty -------------\n" + WHITE);
									break;
								}
							} else if (option == 3) {
								System.out.println("\n");
								vault.viewApplicationSharedByCustomer();
								vault.addAudit(15, loginId);
								logger.info("Customer "+loginId+" viewed shared by me");
								System.out.println("\n");
							} else if (option == 4) {
								System.out.println("\n");
								if (! vault.isCustomerSharedListEmpty()) {
									vault.viewApplicationSharedWithCustomer();
									vault.addAudit(16, loginId);
									logger.info("Customer "+loginId+" viewed shared with me");
									System.out.println("\n");
								} else {
									System.out.println(
											BLUE + "------ No body shared applications with you --------\n" + WHITE);
								}
							}

							else if (option == 5) {
								System.out.println("\n");
								 vault.addAudit(17, loginId);
								 logger.info("Customer "+loginId+" entered trash module");
								if (!vault.isCurrentCustomerHasApplication()) {
									int subOption = 0;
									while (subOption != 4) {
										System.out.print(
												"1.View All Deleted Account\n2.Restore \n3.Delete Permanently\n4.Exit\nEnter the option : ");
										subOption = sc.nextInt();
										System.out.println("\n");
										if (subOption == 1) {
											vault.viewAllApplicationInTrash();
											logger.info("Customer "+loginId+" viewed all deleted application ");
											System.out.println(BLUE + "-------------- END --------------\n" + WHITE);
											vault.addAudit(18, loginId);
										} else if (subOption == 2) {
											vault.viewNamesOfTrashApplicationOfCustomer();
											System.out.print("Enter the index of account you want to restore : ");
											int choice = (sc.nextInt() - 1);
											logger.info("Customer "+loginId+" restore "+choice);
											if (choice > -1 && choice < vault.totalNoTrashApplication()) {
												vault.restore(choice);
												System.out.println(GREEN
														+ "--------Restored Successfully --------------\n" + WHITE);
												if (vault.totalNoTrashApplication() == 0) {
													subOption = 4;
												}
											} else {
												System.out.println(RED
														+ "///////////// Invalid index ///////////////////\n" + WHITE);
											}
										} else if (subOption == 3) {
											vault.viewAllApplicationInTrash();
											System.out.print(
													"Enter the index of account you want to delete permanenently : ");
											int choice = (sc.nextInt() - 1);
											if (choice > -1 && choice < vault.totalNoTrashApplication()) {
												vault.permanentlyDelete(choice);
												logger.info("Customer "+loginId+" deleted permanently "+choice);
												System.out.println(GREEN
														+ "-------- Deleted Successfully --------------\n" + WHITE);
												vault.addAudit(19, loginId);
												if (vault.totalNoTrashApplication() == 0) {
													subOption = 4;
												}
											} else {
												System.out.println(
														RED + "////////////////// Invalid index  /////////////////\n"
																+ WHITE);
											}
										} else if (subOption != 4) {
											System.out
													.println(RED + "------------Invalid Option-------------\n" + WHITE);
										}
									}
								} else {
									System.out.println(CYAN + "--------------Trash is empty------------\n" + WHITE);
								}
								vault.addAudit(20, loginId);
							}
                else if(option==6){
                    int attempt=0;
                    System.out.println("\n");
                    logger.info("Customer "+loginId+" Entered update user module ");
                    while(attempt<3){
                    System.out.print("Enter your vault password : ");
                    String checkPassword=sc.nextLine();
                    attempt++;
                    if(vault.checkPasswordOfCustomer(checkPassword)){
                        System.out.println("You want to edit \n1.Name \t2.Master Password\nEnter Your choice");
                        int choice=sc.nextInt();
                        sc.nextLine();
                        if(choice==1){
                            System.out.print("Enter the new Name : ");
                            String name=sc.nextLine();
                            vault.updateUser("name",name);
                            logger.info("Customer "+loginId+" updated name  ");
                            System.out.println(GREEN+"----Updated Successfully----\n"+WHITE);
                            vault.addAudit(21,loginId);
                        }
                        else if(choice==2){
                            System.out.println("Enter the new Password : ");
                            String newPassword=sc.nextLine();
                            vault.updateUser("password",newPassword);
                            logger.info("Customer "+loginId+" updated master password ");
                            System.out.println(GREEN+"----Updated Successfully----\n"+WHITE);
                            vault.addAudit(22,loginId);
                        }
                        else{
                            System.out.println(RED+"----Invalid Choice ----\n"+WHITE);
                        }
                        break;
                    }
                    else{
                        System.out.println(RED+"------ Wrong password try again -------"+WHITE);
                    }
                     if(attempt==3){
                        System.out.println(RED+"\n++++++++++++++++++++ MAX ATTEMPT REACHED ++++++++++++++++++\n");
                        System.out.println("\n----------------------- YOU ACCOUNT IS LOCKED -----------------\n"+WHITE);
                        logger.info("Customer "+loginId+" Locked Customer ");
                        vault.lockCustomer(loginId);
                        option=7;
                        break;
                    }
                    }
                   
                }
							else if (option != 7) {
								System.out.println(BLUE + "-------Invalid Option-------\n" + WHITE);
							}
							System.out.println("-----------------------------------\n");
						}
						System.out.println(
								MAGNETA + "--------------------- Logged Out Successfully -------------------------");
					} else {
						System.out.println(RED + "\n ----------- YOUR ACCOUNT IS LOCKED PLEASE CONTACT THE ADMIN ----------\n"+ WHITE);
					}
				}
				vault.addAudit(23, loginId);
			}
    System.out.println(MAGNETA+"\nThank You Visit Again !!"+WHITE);
		} catch (InputMismatchException e) {
			logger.error("Input MisMatch Error");
			System.out.println("Input type mismatched Please try again");
		} catch (Exception e) {
			logger.info(e.getMessage());
			System.out.println(e.getMessage());
		} finally {
			sc.close();
		}
	}

}
