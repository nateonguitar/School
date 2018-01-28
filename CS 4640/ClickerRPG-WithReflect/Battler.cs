using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class Battler : MonoBehaviour {

    public int myID = -1;
    public int currentHP = 20;
    public int damage = 1;
    public bool isDead = false;

    public BattleController battleController;
    public GameObject bulletPrefab;
    //public GameObject defendLine;
    //public GameObject reflectLine;

    private float attackCooldown = 0f;
    private float defendTimer = 0f;


	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        attackCooldown -= Time.deltaTime;
        defendTimer -= Time.deltaTime;
	}


    public void DoAttack(float cooldown)
    {
        if (attackCooldown <= 0f)
        {
            SpawnBullet(transform.position, transform.forward, 8f);
            attackCooldown = cooldown;
            
        }
    }

    public void DoDefend(float cooldown)
    {
        if (defendTimer <= 0f)
        {
            defendTimer = cooldown;
        }

        GameObject[] bullets = GameObject.FindGameObjectsWithTag("Bullet");
        for (int i=0; i<bullets.Length; i++)
        {
            // now the bullet knows where it was when the defend button was clicked
            bullets[i].GetComponent<Bullet>().xPosWhenDefendClicked = bullets[i].GetComponent<Bullet>().transform.position.x;
        }
    }

    public bool IsDefending()
    {
        return (defendTimer > 0f);
    }



    public void TakeDamage(int val)
    {
        currentHP -= Mathf.Min(val, currentHP);
        battleController.OnBattlerHit(myID, val);

        if (currentHP <= 0 && !isDead)
        {
            battleController.OnBattlerDeath(myID);
            isDead = true;
        }
    }


    public void SpawnBullet(Vector3 position, Vector3 direction, float speed)
    {
        GameObject bObj = Instantiate(bulletPrefab, position, Quaternion.identity);
        Bullet b = bObj.GetComponent<Bullet>();

        b.myCreator = gameObject;
        b.battleController = battleController;
        b.damage = damage;
        b.SetVelocity(direction * speed);
    }



}
