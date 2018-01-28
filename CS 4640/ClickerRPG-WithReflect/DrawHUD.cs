using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DrawHUD : MonoBehaviour {

    public Image imgPlayerHealth;
    public Image imgEnemyHealth;

    public GameObject damagePrefab;
    public GameObject reflectPrefab;


	// Use this for initialization
	void Start () {
        
	}
	
	// Update is called once per frame
	void Update () {
		
	}


    public void SetPlayerHP(float ratio)
    {
        imgPlayerHealth.fillAmount = ratio;
    }

    public void SetEnemyHP(float ratio)
    {
        imgEnemyHealth.fillAmount = ratio;
    }


    public void CreateDamageIndicator(int val, Vector3 worldPos)
    {
        Vector3 screenPos = Camera.main.WorldToScreenPoint(worldPos) + (Vector3.up * 64f);

        GameObject g = Instantiate(damagePrefab, screenPos, Quaternion.identity, transform);
        //DamageIndicator d = g.GetComponent<DamageIndicator>();
        g.GetComponent<Text>().text = val.ToString();
    }

    public void CreateReflectIndicator(Vector3 worldPos)
    {
        Vector3 screenPos = Camera.main.WorldToScreenPoint(worldPos) + (Vector3.up * 64f);

        GameObject g = Instantiate(reflectPrefab, screenPos, Quaternion.identity, transform);
        //DamageIndicator d = g.GetComponent<DamageIndicator>();
        g.GetComponent<Text>().text = "Reflect!";
    }
}
