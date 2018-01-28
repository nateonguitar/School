using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BattleController : MonoBehaviour {

    public GameObject battlerPrefab;
    public int numEnemies = 1;

    private BattleEntity[] battleEntities;

    private Vector3 centerPosition = Vector3.zero;
    private Vector3 playerOffset = new Vector3(-8f, 0f, 0f);
    private Vector3 enemyOffset = new Vector3(8f, 0f, 0f);

    public DrawHUD hudObject;


	// Use this for initialization
	void Start () {
        battleEntities = new BattleEntity[numEnemies + 1];
        InitializeBattleEntities();


	}
	
	// Update is called once per frame
	void Update () {
        HandleEnemyBehavior();

        
	}


    public void HandleEnemyBehavior()
    {
        for (int i = 1; i <= numEnemies; i++)
        {
            if (!battleEntities[i].isDead)
            {
                Battler b = battleEntities[i].battler;
                b.DoAttack(2f);
            }
        }
    }



    public void OnBattlerHit(int battlerID, int damage)
    {
        int currentHP = battleEntities[battlerID].battler.currentHP;
        int maxHP = 20;

        if (battlerID == 0)
        {
            hudObject.SetPlayerHP((float)currentHP / (float)maxHP);
        }
        else
        {
            hudObject.SetEnemyHP((float)currentHP / (float)maxHP);
        }

        hudObject.CreateDamageIndicator(damage, battleEntities[battlerID].gameObject.transform.position);
    }

    public void OnBattlerDeath(int battlerID)
    {
        battleEntities[battlerID].isDead = true;
    }

    public void OnButtonClick_Attack()
    {
        if (battleEntities[0].isDead)
            return;

        battleEntities[0].battler.DoAttack(0.5f);
    }

    public void OnButtonClick_Defend()
    {
        if (battleEntities[0].isDead)
            return;

        // will quickly decrement this and check if it's below zero
        battleEntities[0].battler.DoDefend(2f);
    }



    private void InitializeBattleEntities()
    {
        BattleEntity pEntity = new BattleEntity();
        pEntity.gameObject = Instantiate(battlerPrefab, centerPosition + playerOffset, Quaternion.Euler(0f, 90f, 0f));
        pEntity.gameObject.name = "BattleEntity_Player";
        pEntity.gameObject.tag = "Player";
        pEntity.battler = pEntity.gameObject.GetComponent<Battler>();
        pEntity.battler.myID = 0;
        pEntity.isPlayer = true;
        pEntity.battler.battleController = this;
        battleEntities[0] = pEntity;

        for (int i = 1; i <= numEnemies; i++)
        {
            BattleEntity eEntity = new BattleEntity();
            eEntity.gameObject = Instantiate(battlerPrefab, centerPosition + enemyOffset, Quaternion.Euler(0f, 270f, 0f));
            eEntity.gameObject.name = "BattleEntity_Enemy" + i.ToString();
            eEntity.gameObject.tag = "Enemy";
            eEntity.battler = eEntity.gameObject.GetComponent<Battler>();
            eEntity.battler.myID = i;
            eEntity.battler.battleController = this;

            battleEntities[i] = eEntity;
        }
    }

    public void ShowReflectIndicator()
    {
        // since the player is always at index 0 lets use it
        hudObject.CreateReflectIndicator(battleEntities[0].gameObject.transform.position);
    }




    public class BattleEntity
    {
        public GameObject gameObject;
        public Battler battler;
        public bool isPlayer = false;
        public bool isDead = false;
    }
}
