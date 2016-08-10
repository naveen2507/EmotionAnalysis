
/*** File Format ****/
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




