using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DatabaseConnector : MonoBehaviour {
    string url = "http://nateonguitar.com/Unity/API_Call_Test/test.php";
    WWWForm form;
    WWW www;
    string user;
    int numberOfSpacePresses = 0;
    public Text outputText;

    void Start () {
        
    }

    void Update()
    {
        if (Input.GetKeyDown("space"))
        {
            numberOfSpacePresses++;

            user = 
@"
{
    ""name"": ""Nate"",
    ""numberOfSpacePresses"": " + numberOfSpacePresses + @"
}
";
            StartCoroutine(insertUser(user));
        }
    }

    IEnumerator insertUser(string user)
    {
        form = new WWWForm();
        form.AddField("json", user);

        www = new WWW(url, form);

        // Wait until the download is done
        yield return www;

        if (!string.IsNullOrEmpty(www.error))
        {
            print("Error downloading: " + www.error);
        }
        else
        {
            // show the highscores
            outputText.text = www.text;
        }
    }
}
