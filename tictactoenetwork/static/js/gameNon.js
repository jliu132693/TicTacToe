$(document).ready(function() {

	var EMPTY_CONST = 0;
	var PLAYER_CONST = 1;
	var OPPONENT_CONST = 2;
	var BOARD_SIZE = 9;
	var PLAYER_UNIT = "X";
	var OPPONENT_UNIT = "O";
	var currentBoard = [];

	var opponentChooseCorner = false;
	var opponentChooseMiddle = false;
	
	for (var i = 0; i < BOARD_SIZE; i++) {
		currentBoard[i] = EMPTY_CONST;
	} 
	
	$("#1").click(function() {
		opponentChooseCorner = true;
		opponentChooseMiddle = false;
		onClick(0); 
		
	});

	$("#2").click(function() {
		opponentChooseCorner = false;
		opponentChooseMiddle = false;
		onClick(1); 
	});
	
	$("#3").click(function() {
		opponentChooseCorner = true;
		opponentChooseMiddle = false;
		onClick(2); 
	});
	
	$("#4").click(function() {
		opponentChooseCorner = false;
		opponentChooseMiddle = false;
		onClick(3); 
	});
	
	$("#5").click(function() {
		opponentChooseCorner = false;
		opponentChooseMiddle = true;
		onClick(4); 
	});
	
	$("#6").click(function() {
		opponentChooseCorner = false;
		opponentChooseMiddle = false;
		onClick(5); 
	});
	
	$("#7").click(function() {
		opponentChooseCorner = true;
		opponentChooseMiddle = false;
		onClick(6); 
	});
	
	$("#8").click(function() {
		opponentChooseCorner = false;
		opponentChooseMiddle = false;
		onClick(7); 
	});
	
	$("#9").click(function() {
		opponentChooseCorner = true;
		opponentChooseMiddle = false;
		onClick(8);
	});
	
	/**
	 * TODO - document
	 */
	function onClick(i) {
		if (currentBoard[i] === EMPTY_CONST) {
			currentBoard[i] = PLAYER_CONST;
			$("#"+(i+1)).html(generateMoveHtml(PLAYER_UNIT));
			
			generateMove(i);
			
			if (checkForEndGame(true)) { // if game over, true to generate text
				return;
			} 
	
			isFullBoard(true); // if game over, true to generate text
		}
	}
	
	/**
	 * If generateEndGameText is false, the function is merely called to test an input.
	 */
	function isFullBoard(generateEndGameText) {
		/*if (checkForEndGame()) {
			return;
		}*/
		
		
	
		var emptyFound = false;
		for (var i = 0; i < BOARD_SIZE; i++) {
			emptyFound |= currentBoard[i] === EMPTY_CONST;
		}
		
		if (!emptyFound && generateEndGameText) {

			if (confirm("Board is full: draw! Do you want to play again?")) {
				// TODO - reload page
				location.reload();
			
			} else {
				document.location.href = "/";
				// TODO - change url
			}

			return;
		}
		
		return !emptyFound;
	}
	
	/**
	 * Function asks question, if answer is yes, then it takes opportunity. 
	 * Idea is to maximize scores.
	 * 1. Can I win?
	 * 2. Can I tie?
	 * 3. Can I prevent you from winning?
	 * 4. Various cases
	 * 		- did opponent choose corner?
	 *			choose middle then adjacent corner
	 *		- did opponent choose middle?
	 *			choose corner
	 *		- remaining choice. 
	 *			choose across or ?
	 */
	function generateMove(opponentMove) {
		var i = 0;
		var winMoveFound = false;
		var tieMoveFound = false;

		while (i < BOARD_SIZE) {
		
			if  (currentBoard[i] === EMPTY_CONST) {
				
				// test possible move
				currentBoard[i] = OPPONENT_CONST;
				tieMoveFound |= isFullBoard(false); // test move; false to generate no text
				winMoveFound |= checkForEndGame(false); // test move; false to generate no text

				currentBoard[i] = EMPTY_CONST;

			}
			i++;
		}

		if (winMoveFound) {
			// choose win move
			i = 0;
			winMoveFound = false;
			while (i < BOARD_SIZE) {
		
				if  (currentBoard[i] === EMPTY_CONST) {
					
					// test possible move
					currentBoard[i] = OPPONENT_CONST;
					if(checkForEndGame(false)) {  // test move; false to generate no text
						$("#" + (i+1)).html(generateMoveHtml(OPPONENT_UNIT));
						break;
					} else {
						currentBoard[i] = EMPTY_CONST;
					}


				}
				i++;
			}

		} else if (tieMoveFound) {
			// choose tie move
			i = 0;
			tieMoveFound = false;
			while (i < BOARD_SIZE) {
		
				if  (currentBoard[i] === EMPTY_CONST) {
					
					// test possible move
					currentBoard[i] = OPPONENT_CONST;
					if(isFullBoard(false)) {  // test move; false to generate no text
						$("#" + (i+1)).html(generateMoveHtml(OPPONENT_UNIT));
						break;
					} else {
						currentBoard[i] = EMPTY_CONST;
					}

				}
				i++;
			}
		} else {
			// now check for opponent victory
			i = 0;
			tieMoveFound = false;
			winMoveFound = false;
			while (i < BOARD_SIZE) {
		
				if  (currentBoard[i] === EMPTY_CONST) {
					
					// test possible move
					currentBoard[i] = PLAYER_CONST;
					tieMoveFound |= isFullBoard(false);
					winMoveFound |= checkForEndGame();

					currentBoard[i] = EMPTY_CONST;

				}
				i++;
			}

			if (winMoveFound) {
				// choose win move
				i = 0;
				winMoveFound = false;
				while (i < BOARD_SIZE) {
			
					if  (currentBoard[i] === EMPTY_CONST) {
						
						// test possible move
						currentBoard[i] = PLAYER_CONST;
						if(checkForEndGame(false)) {  // test move; false to generate no text
							currentBoard[i] = OPPONENT_CONST; // prevent player from win
							
							$("#" + (i+1)).html(generateMoveHtml(OPPONENT_UNIT));
							break;
						} else {
							currentBoard[i] = EMPTY_CONST;
						}
						

					}
					i++;
				}

			} else {
				// here, choose adjacent square


				// first check middle if opponent hasn't chosen the corner
				if (opponentChooseCorner) {
					if (currentBoard[4] == EMPTY_CONST) {
						currentBoard[4] = OPPONENT_CONST; // prevent player from win
						$("#" + (4+1)).html(generateMoveHtml(OPPONENT_UNIT));	
						return;
					} else if (currentBoard[0] == PLAYER_CONST) {
						if (currentBoard[1] == EMPTY_CONST && currentBoard[7] != PLAYER_CONST) {
							// choose and return
							currentBoard[1] = OPPONENT_CONST; // prevent player from win
							$("#" + (1+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						} else if (currentBoard[3] == EMPTY_CONST && currentBoard[7] === PLAYER_CONST) {
							// choose and return
							currentBoard[3] = OPPONENT_CONST; // prevent player from win
							$("#" + (3+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						} 
					} else if (currentBoard[2] == PLAYER_CONST) {
						if (currentBoard[1] == EMPTY_CONST && currentBoard[7] != PLAYER_CONST) {
							// choose and return
							currentBoard[1] = OPPONENT_CONST; // prevent player from win
							$("#" + (1+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						} else if (currentBoard[5] == EMPTY_CONST && currentBoard[7] === PLAYER_CONST) {
							// choose and return
							currentBoard[5] = OPPONENT_CONST; // prevent player from win
							$("#" + (5+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						} 
					} else if (currentBoard[6] == PLAYER_CONST) {
						if (currentBoard[3] == EMPTY_CONST) {
							// choose and return
							currentBoard[3] = OPPONENT_CONST; // prevent player from win
							$("#" + (3+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						} else if (currentBoard[7] === EMPTY_CONST) {
							// choose and return
							currentBoard[7] = OPPONENT_CONST; // prevent player from win
							$("#" + (7+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						} 
					} else if (currentBoard[8] === PLAYER_CONST) {
						 if (currentBoard[5] === EMPTY_CONST) {
							// choose and return
							currentBoard[5] = OPPONENT_CONST; // prevent player from win
							$("#" + (5+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						} else if (currentBoard[7] === EMPTY_CONST) {
							// choose and return
							currentBoard[7] = OPPONENT_CONST; // prevent player from win
							$("#" + (7+1)).html(generateMoveHtml(OPPONENT_UNIT));	
							return;
						}
					}

				} else if (opponentChooseMiddle) {
					if (currentBoard[0] == EMPTY_CONST) {
						// then check top left corner	
						currentBoard[0] = OPPONENT_CONST; // prevent player from win
						$("#" + (0+1)).html(generateMoveHtml(OPPONENT_UNIT));	
						return;
					}  else if (currentBoard[2] == EMPTY_CONST) {
						// then check top right
						currentBoard[2] = OPPONENT_CONST; // prevent player from win
						$("#" + (2+1)).html(generateMoveHtml(OPPONENT_UNIT));	
						return;
					} else if (currentBoard[6] == EMPTY_CONST) {
						// then check bottom left 
						currentBoard[6] = OPPONENT_CONST; // prevent player from win
						$("#" + (6+1)).html(generateMoveHtml(OPPONENT_UNIT));	
						return;
					} else if (currentBoard[8] == EMPTY_CONST) {
						// then check bottom right
						currentBoard[8] = OPPONENT_CONST; // prevent player from win
						$("#" + (8+1)).html(generateMoveHtml(OPPONENT_UNIT));	
						return;
					}
				} else {
				    else if (currentBoard[3] == PLAYER_CONST) {
						if (currentBoard[0] == EMPTY_CONST) {
							// choose and return
							currentBoard[0] = OPPONENT_CONST; // prevent player from win
							$("#" + (0+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						} else if (currentBoard[6] == EMPTY_CONST) {
							// choose and return
							currentBoard[6] = OPPONENT_CONST; // prevent player from win
							$("#" + (6+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						}
					} else if (currentBoard[2] == PLAYER_CONST) {
						if (currentBoard[0] == EMPTY_CONST) {
							// choose and return
							currentBoard[0] = OPPONENT_CONST; // prevent player from win
							$("#" + (0+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						} else if (currentBoard[3] === EMPTY_CONST) {
							// choose and return
							currentBoard[3] = OPPONENT_CONST; // prevent player from win
							$("#" + (3+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						}
					} else if (currentBoard[5] === PLAYER_CONST) {
						 if (currentBoard[2] === EMPTY_CONST) {
							// choose and return
							currentBoard[2] = OPPONENT_CONST; // prevent player from win
							$("#" + (2+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						} else if (currentBoard[8] === EMPTY_CONST) {
							// choose and return
							currentBoard[8] = OPPONENT_CONST; // prevent player from win
							$("#" + (8+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						}
					} else if (currentBoard[7] === PLAYER_CONST) {
						 if (currentBoard[6] === EMPTY_CONST) {
							// choose and return
							currentBoard[6] = OPPONENT_CONST; // prevent player from win
							$("#" + (6+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						} else if (currentBoard[8] === EMPTY_CONST) {
							// choose and return
							currentBoard[8] = OPPONENT_CONST; // prevent player from win
							$("#" + (8+1)).html(generateMoveHtml(OPPONENT_UNIT));
							return;
						}
					}
				}


				i = 0;
				winMoveFound = false;
				var previousSquareWasOpponent = false;

				while (i < BOARD_SIZE) {
			
					if (currentBoard[i] === PLAYER_CONST) {
						previousSquareWasOpponent = true;
					} else if (currentBoard[i] === OPPONENT_CONST) {
						previousSquareWasOpponent = false;
					}
					else {
						if (previousSquareWasOpponent) {
							currentBoard[i] = OPPONENT_CONST; // prevent player from win
							$("#" + (i+1)).html(generateMoveHtml(OPPONENT_UNIT));
							break;
						} 
					}
						
					i++;
				}

				// if you make it to end, chose random
			}

		}

	}
	
	/**
	 * TODO - document
	 */ 
	function generateMoveHtml(playerUnit) {
		// TODO - allow for player/opponent color selection (settings)
		
		var textColor = "red";

		if (playerUnit === PLAYER_UNIT) {
			textColor = "blue";
		}

		return "<pre style='color:" + textColor + "'>    <b>" + playerUnit + "</b>      </pre>";
	}


	/**
	 * If generateEndGameText is false, the function is merely called to test an input.
	 */
	function checkForEndGame(generateEndGameText) {
		// TODO - optimize
		
		var winningCombinations = [[0, 1, 2], [3, 4, 5], 
					[6, 7, 8], [0, 3, 6], [1, 4, 7], [2, 5, 8],
					[0, 4, 8], [2, 4, 6]];
		
		var endGame = false;
		
		// TODO - check for false as well as combination of
		// 2 opponent and 1 empty
		
		for (var i = 0; i < winningCombinations.length; i++) {
			if (equalityOfThree(winningCombinations[i])) {
				
				if (generateEndGameText) {
					var resultString = "";
				if (currentBoard[winningCombinations[i][0]]=== OPPONENT_CONST) {
					resultString = "YOU LOSE!";
					alterUserPoints(-1); // subtract a point 
				} else {
					resultString = "YOU WIN!";
					alterUserPoints(1); // add a user point
				}
				
				//$("#result").html(resultString);
				if (confirm(resultString + " Do you want to play again?")) {
					// TODO - reload page
					location.reload();
				
				} else {
					document.location.href = "/";
					// TODO - change url
				}
				}
				
				
				return true;
			} 
			
		}
		 
		return false;
	}

	/** 
	 * TODO - document
	 */
	function alterUserPoints(points) {
		$.ajax({
			url:"/play",
			data: {
				pointChange: points
			}, 
			type:"POST", 
			error: function(xhr, status, errorThrown) {
				alert("Error updating points!");
			}
		});
	}
	
	/**
	 * TODO - document
	 */
	function equalityOfThree(combination) {
		
		return currentBoard[combination[0]] 
				=== currentBoard[combination[1]] && 
				
				currentBoard[combination[0]] 
				=== currentBoard[combination[2]] && 
				currentBoard[combination[0]] !== EMPTY_CONST;
	}
	

});