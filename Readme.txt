*************************** Emotion Analysis *******************************************************

This project deals with the problem of Emotion analysis specially in short text messages like Twitter. THe work deals with analyzing emotions
and categorizing text into six different class which are:
1.Sad
2.Anger
3.Fear
4.Happy
5.Disgust
6.Surprise

The training data is extracted from twitter using some hashtags , so we get an auto annotated training set as each hash tag is related to
a label/category. The training data is then pre-processed to remove unnecessary content of the text. It's further filtered using Cosine 
Similarity to remove duplicate data from training set.

The work also focus on the other aspect of emotion analysis , where it tilts to a concept similar to soft clustering , but in the aspect of 
classification. The project proposes "Ranked Result" concept of classification and evaluates the model on top 'K' predictions which 
drastically improve the accuracy measures. The results are explained in a better and detailed way in the paper. 

The project implements a multi class perceptron which deals with training the model and then using the model for predicting the test
data. 


**************************************************************************************************************
*************************Code document in  brief***********************************

/***Raw File Format ****/
Tab Separated Columns: ClassLabel,HashTag,DateTime,TweetID,UserName,Language,UserProfileName,Tweet
Eg:
		surprise
		#surprise
		2016-03-24 15:15:15 +0100
		713006264251523072
		
		@ErikaRriveraa
		es
		Erika Rivera
		#bohochic #jasperring #etsyboho #etsy #fashion #etsyseller #bijou #style #jewelry #silver #naturalstones #surprise … https://t.co/yfSARK295C


ApplicationDetails.java : Stores all configuration and paths used in code 

/******Read Data ******/
Class : com.ims.data.ReadData
Function : 	getDataValues(String fileName)
Input : File
Output : List<TweetVO>
Adds data to TweetVO object 


/******Preprocessing Data******/
Class : com.ims.data.Preprocessing
Description : Preprocessed and filter similar data using cosine similarity

/****Evaluation Module ***/

Package Name : com.ims.evaluation
SubPackages :com.ims.evaluation.vo
			 com.ims.evaluation.process

		Classes: 
		
		com.ims.evaluation.vo.EvaluationVO : This class value object has confusion matrix , precision ,recall, f-score for each and every class.
		
		com.ims.evaluation.vo.ResultVO : This class value object has aggeregate score of elemnts of confusion matrix and f-score , micro score, macro score and Map object of details of each and every class as evaluationMatrix.
		
		com.ims.evaluation.process.Evaluation : Evaluation class gives you the measures of accuracy, precision,recall,f-score for each class  
		
		com.ims.evaluation.process.Results : This is main class for result evaluation. 
			  Input : Data File tab separated
			  Data Format : PredictedClass, GoldClass , and rest fields as per in orginal file
			  Output :
			  Prints evaluation metrics for each class and print macro and micro F-score.
			  Function :
			  getData(String fileName) : Reads the data and get evalaution for each class.
			  getResults(ResultVO metric) : Prints final result and computes final scores.
			  
	 

			 
/****Perceptron Module ***/

Training : Train.java
Test : Test.java
Multi CLass Perceptron : MultiClassPerceptron.java
Perceptron Modelling : PerceptronModelling.java 

Description : Train.java has main for training perceptron while Test.java is for testing purpose.
Both Train.java and Test.java calls train and test method from PerceptronModelling.java respectively.MulticLCassPereptron.java has methods 
for the core implementation of perceptron.

